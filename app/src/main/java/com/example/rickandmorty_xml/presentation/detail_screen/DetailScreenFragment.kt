package com.example.rickandmorty_xml.presentation.detail_screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.databinding.FragmentDetailScreenBinding
import com.example.rickandmorty_xml.presentation.adapters.PagingAdapter
import com.example.rickandmorty_xml.presentation.base.BaseFragment
import com.example.rickandmorty_xml.util.setupLoadingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailScreenFragment : BaseFragment<FragmentDetailScreenBinding, DetailScreenVM>(
    FragmentDetailScreenBinding::inflate,
    DetailScreenVM::class.java
) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: DetailScreenFragmentArgs by navArgs()
        viewModel.loadDetailState(args.characterId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
    }

    private fun subscribe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.detailScreenState.collect(this@DetailScreenFragment::setupDetails)
                }
            }
        }
    }

    private fun setupDetails(states: DetailScreenUIStates) {
        binding.apply {

            binding.loadingLayout.setupLoadingScreen(states.isLoading)

            characterImage.load(states.model?.imageUrl) {
                crossfade(true)
                crossfade(PagingAdapter.CROSS_FADE_MILLIS)
            }

            characterName.text = states.model?.characterName

            statusAndSpecies.text = requireContext().getString(
                R.string.isAliveSpecies,
                states.model?.isAlive,
                states.model?.species
            )

            val isAliveDotResource = when(states.model?.isAlive) {
                PagingAdapter.ALIVE -> { R.drawable.green_dot }
                PagingAdapter.DEAD -> { R.drawable.red_dot }
                else -> { R.drawable.gray_dot }
            }

            isAliveDot.setImageResource(isAliveDotResource)
        }
    }
}

