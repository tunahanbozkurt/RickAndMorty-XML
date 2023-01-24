package com.example.rickandmorty_xml.util

import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter

fun PagingDataAdapter<*, *>.withLoadStateHeaderAndFooterAndConfig(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    val config = ConcatAdapter.Config.Builder()
        .setIsolateViewTypes(false).build()

    this.addLoadStateListener { loadStates ->
        header.loadState = loadStates.prepend
        footer.loadState = loadStates.append
    }
    return ConcatAdapter(config, header, this, footer)
}