package com.example.rickandmorty_xml.presentation.main_screen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty_xml.databinding.FragmentMainScreenBinding
import com.example.rickandmorty_xml.presentation.adapters.PagingAdapter
import com.example.rickandmorty_xml.presentation.adapters.PagingLoadStateAdapter
import com.example.rickandmorty_xml.presentation.base.BaseFragment
import com.example.rickandmorty_xml.util.withLoadStateHeaderAndFooterAndConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment : BaseFragment<FragmentMainScreenBinding, MainScreenVM>(
    FragmentMainScreenBinding::inflate,
    MainScreenVM::class.java
) {
    private lateinit var pagingAdapter: PagingAdapter
    private lateinit var pagingLoadStateAdapter: PagingLoadStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
        setupRecyclerView()
        subscribePagination()
    }

    private fun setupLayout() {
        binding.errorViewBinding.retry.setOnClickListener {
            pagingAdapter.retry()
        }
    }

    private fun setupRecyclerView() {
        setupRecyclerViewAdapter()
        setupLayoutManager()
    }

    private fun setupRecyclerViewAdapter() {
        pagingAdapter = PagingAdapter {
            onItemClickListener(it)
        }.also {
            it.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        pagingLoadStateAdapter = PagingLoadStateAdapter { pagingAdapter.retry() }
        binding.recyclerView.apply {
            adapter = pagingAdapter.withLoadStateHeaderAndFooterAndConfig(
                footer = pagingLoadStateAdapter
            )
        }
    }

    private fun onItemClickListener(id: Int) {
        /*TODO*/
    }

    private fun setupLayoutManager() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun subscribePagination() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.pagedItems.collect { pagedData ->
                        pagingAdapter.submitData(pagedData)
                    }
                }

                launch {
                    pagingAdapter.loadStateFlow.collectLatest {
                        binding.errorViewBinding.errorView.isVisible =
                            it.refresh is LoadState.Error && pagingAdapter.itemCount == 0
                    }
                }
            }
        }
    }
}