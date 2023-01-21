package com.example.rickandmorty_xml.presentation.main_screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmorty_xml.databinding.FragmentMainScreenBinding
import com.example.rickandmorty_xml.presentation.adapters.PagingAdapter
import com.example.rickandmorty_xml.presentation.base.BaseFragment
import com.example.rickandmorty_xml.util.setGone
import com.example.rickandmorty_xml.util.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment : BaseFragment<FragmentMainScreenBinding, MainScreenVM>(
    FragmentMainScreenBinding::inflate,
    MainScreenVM::class.java
) {
    private lateinit var pagingAdapter: PagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupLayout()
        loadPagedData()
    }

    private fun setupRecyclerView() {
        pagingAdapter = PagingAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                activity, 2, GridLayoutManager.VERTICAL, false
            )
            adapter = pagingAdapter
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

                launch {
                    pagingAdapter.loadStateFlow.collectLatest {
                        when(it.refresh) {
                            is LoadState.Error -> {
                                binding.errorView.setVisible()
                            }
                            else -> {
                                binding.errorView.setGone()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupLayout() {
        binding.retry.setOnClickListener {
            pagingAdapter.retry()
        }
    }
}