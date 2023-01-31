package com.example.rickandmorty_xml.util

import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter

fun PagingDataAdapter<*, *>.withLoadStateFooterAndConfig(
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    val config = ConcatAdapter.Config.Builder()
        .setIsolateViewTypes(false).build()

    this.addLoadStateListener { loadStates ->
        if (loadStates.refresh is LoadState.Error) {
            footer.loadState = if (this.itemCount == 0)
                loadStates.append else loadStates.refresh
        } else {
            footer.loadState = loadStates.append
        }
    }
    return ConcatAdapter(config, this, footer)
}

fun PagingDataAdapter<*, *>.withLoadStateHeaderAndFooterAndConfig(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    val config = ConcatAdapter.Config.Builder()
        .setIsolateViewTypes(false).build()

    this.addLoadStateListener { loadStates ->
        if (loadStates.refresh is LoadState.Error) {
            footer.loadState = if (this.itemCount == 0)
                loadStates.append else loadStates.refresh
        } else {
            footer.loadState = loadStates.append
        }
    }
    return ConcatAdapter(config, header, this, footer)
}