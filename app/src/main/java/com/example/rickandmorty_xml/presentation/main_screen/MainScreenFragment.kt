package com.example.rickandmorty_xml.presentation.main_screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.Config
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.ConcatAdapter.Config
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmorty_xml.databinding.FragmentMainScreenBinding
import com.example.rickandmorty_xml.presentation.adapters.PagingAdapter
import com.example.rickandmorty_xml.presentation.adapters.PagingLoadStateAdapter
import com.example.rickandmorty_xml.presentation.base.BaseFragment
import com.example.rickandmorty_xml.util.setGone
import com.example.rickandmorty_xml.util.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val SPAN_COUNT = 2

@AndroidEntryPoint
class MainScreenFragment : BaseFragment<FragmentMainScreenBinding, MainScreenVM>(
    FragmentMainScreenBinding::inflate,
    MainScreenVM::class.java
) {
    private lateinit var pagingAdapter: PagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadPagedData()
    }

    private fun setupRecyclerView() {
        pagingAdapter = PagingAdapter()
        setupLayoutManager()
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter() {
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
                                1
                            }
                            else -> {
                                1
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadPagedData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loadPagedData().collect { pagedData ->
                        pagingAdapter.submitData(pagedData)
                    }
                }
            }
        }
    }

    private fun PagingDataAdapter<*, *>.withLoadStateHeaderAndFooterAndConfig(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        val config = Config.Builder()
            .setIsolateViewTypes(false).build()

        this.addLoadStateListener { loadStates ->
            header.loadState = loadStates.prepend
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(config, header, this, footer)
    }
}