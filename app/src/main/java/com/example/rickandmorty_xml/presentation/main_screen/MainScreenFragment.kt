package com.example.rickandmorty_xml.presentation.main_screen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty_xml.databinding.FragmentMainScreenBinding
import com.example.rickandmorty_xml.presentation.adapters.PagingAdapter
import com.example.rickandmorty_xml.presentation.adapters.PagingLoadStateAdapter
import com.example.rickandmorty_xml.presentation.base.BaseFragment
import com.example.rickandmorty_xml.util.withLoadStateHeaderAndFooterAndConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val SPAN_COUNT = 2
const val SINGLE_SPAN_COUNT = 1

@AndroidEntryPoint
class MainScreenFragment : BaseFragment<FragmentMainScreenBinding, MainScreenVM>(
    FragmentMainScreenBinding::inflate,
    MainScreenVM::class.java
) {
    private lateinit var pagingAdapter: PagingAdapter

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
            onItemClickListener()
        }
        binding.recyclerView.apply {
            adapter = pagingAdapter.withLoadStateHeaderAndFooterAndConfig(
                header = PagingLoadStateAdapter {
                    pagingAdapter.retry()
                },
                footer = PagingLoadStateAdapter {
                    pagingAdapter.retry()
                }
            )
        }
    }

    private fun onItemClickListener() {
        /*TODO*/
    }

    private fun setupLayoutManager() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                activity, SPAN_COUNT, GridLayoutManager.VERTICAL, false
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when(adapter?.getItemViewType(position)) {

                            PagingLoadStateAdapter.ITEM_TYPE -> {
                                SPAN_COUNT
                            }
                            PagingAdapter.ITEM_TYPE -> {
                                SINGLE_SPAN_COUNT
                            }
                            else -> {
                                SINGLE_SPAN_COUNT
                            }
                        }
                    }
                }
            }
        }
    }

    private fun subscribePagination() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loadPagedData().collect { pagedData ->
                        pagingAdapter.submitData(pagedData)
                    }
                }

                launch {
                    pagingAdapter.loadStateFlow.collectLatest {
                        binding.errorViewBinding.errorView.isVisible = it.refresh is LoadState.Error
                    }
                }
            }
        }
    }
}