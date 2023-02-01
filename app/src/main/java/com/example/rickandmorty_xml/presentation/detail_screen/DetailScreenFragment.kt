package com.example.rickandmorty_xml.presentation.detail_screen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.databinding.FragmentDetailScreenBinding
import com.example.rickandmorty_xml.domain.model.CharacterCardModel
import com.example.rickandmorty_xml.presentation.adapters.CharactersPagingAdapter
import com.example.rickandmorty_xml.presentation.adapters.DetailsAdapter
import com.example.rickandmorty_xml.presentation.base.BaseFragment
import com.example.rickandmorty_xml.util.setupLoadingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailScreenFragment : BaseFragment<FragmentDetailScreenBinding, DetailScreenVM>(
    FragmentDetailScreenBinding::inflate,
    DetailScreenVM::class.java
) {
    private lateinit var args: DetailScreenFragmentArgs
    private lateinit var charactersPagingAdapter: CharactersPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = navArgs<DetailScreenFragmentArgs>().value
        viewModel.loadDetailState(args.characterId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleError()
        subscribe()
    }

    private fun handleError() {
        binding.errorLayout.retry.setOnClickListener {
            viewModel.loadDetailState(args.characterId)
        }
    }

    private fun subscribe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.detailScreenState.collect(this@DetailScreenFragment::setupDetails)
                }

                launch {
                    viewModel.locationList.collect(this@DetailScreenFragment::setupNestedRecylerViews)
                }
            }
        }
    }

    private fun setupDetails(states: DetailScreenUIStates) {
        binding.apply {

            binding.loadingLayout.setupLoadingScreen(states.isLoading)
            binding.errorLayout.errorView.isVisible = states.hasError

            characterImage.load(states.model?.imageUrl) {
                crossfade(true)
                crossfade(1000)
            }

            characterName.text = states.model?.characterName

            statusAndSpecies.text = requireContext().getString(
                R.string.isAliveSpecies,
                states.model?.isAlive,
                states.model?.species
            )

            val isAliveDotResource = when (states.model?.isAlive) {
                "Alive" -> {
                    R.drawable.green_dot
                }
                "Dead" -> {
                    R.drawable.red_dot
                }
                else -> {
                    R.drawable.gray_dot
                }
            }

            isAliveDot.setImageResource(isAliveDotResource)
        }
    }

    private fun setupNestedRecylerViews(list: List<CharacterCardModel>) {
        binding.detailsRecyclerview.adapter = DetailsAdapter(list) { characterId ->
            val action = DetailScreenFragmentDirections
                .actionDetailScreenFragmentSelf(characterId)
            findNavController().navigate(action)
        }
        binding.detailsRecyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }
}

