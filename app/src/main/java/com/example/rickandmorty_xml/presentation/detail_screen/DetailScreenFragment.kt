package com.example.rickandmorty_xml.presentation.detail_screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty_xml.databinding.FragmentDetailScreenBinding
import com.example.rickandmorty_xml.presentation.base.BaseFragment
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
                    viewModel.detailScreenState.collect {
                        binding.characterName.text = it?.name
                    }
                }
            }
        }
    }
}

