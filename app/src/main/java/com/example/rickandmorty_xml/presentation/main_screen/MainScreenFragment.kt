package com.example.rickandmorty_xml.presentation.main_screen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty_xml.databinding.FragmentMainScreenBinding
import com.example.rickandmorty_xml.presentation.adapters.CharactersPagingAdapter
import com.example.rickandmorty_xml.presentation.adapters.PagingLoadStateAdapter
import com.example.rickandmorty_xml.presentation.base.BaseFragment
import com.example.rickandmorty_xml.util.setupLoadingScreen
import com.example.rickandmorty_xml.util.withLoadStateFooterAndConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment : BaseFragment<FragmentMainScreenBinding, MainScreenVM>(
    FragmentMainScreenBinding::inflate,
    MainScreenVM::class.java
) {
    private lateinit var charactersPagingAdapter: CharactersPagingAdapter
    private lateinit var pagingLoadStateAdapter: PagingLoadStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
        setupRecyclerView()
        subscribePagination()
    }

    private fun setupLayout() {
        binding.errorViewBinding.retry.setOnClickListener {
            charactersPagingAdapter.retry()
        }
    }

    private fun setupRecyclerView() {
        setupRecyclerViewAdapter()
        setupLayoutManager()
    }

    private fun setupRecyclerViewAdapter() {
        charactersPagingAdapter = CharactersPagingAdapter(this::onItemClickListener)
        pagingLoadStateAdapter = PagingLoadStateAdapter { charactersPagingAdapter.retry() }
        binding.recyclerView.apply {
            adapter = charactersPagingAdapter.withLoadStateFooterAndConfig(
                footer = pagingLoadStateAdapter
            )
        }
    }

    private fun onItemClickListener(characterId: Int) {
        val action = MainScreenFragmentDirections
            .actionMainScreenFragmentToDetailScreenFragment(characterId)
        findNavController().navigate(action)
    }

    private fun setupLayoutManager() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun subscribePagination() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.pagedItems.collectLatest(charactersPagingAdapter::submitData)
                }

                launch {
                    charactersPagingAdapter.loadStateFlow.collectLatest {
                        val isError =
                            it.refresh is LoadState.Error && charactersPagingAdapter.itemCount == 0

                        val isLoading =
                            it.refresh is LoadState.Loading && charactersPagingAdapter.itemCount == 0

                        binding.errorViewBinding.errorView.isVisible = isError
                        binding.loadingLayout.setupLoadingScreen(isLoading)
                    }
                }
            }
        }
    }
}