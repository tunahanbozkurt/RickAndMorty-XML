package com.example.rickandmorty_xml.presentation.detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.databinding.FragmentDetailScreenBinding
import com.example.rickandmorty_xml.presentation.base.BaseFragment

class DetailScreenFragment : BaseFragment<FragmentDetailScreenBinding, DetailScreenVM>(
    FragmentDetailScreenBinding::inflate,
    DetailScreenVM::class.java
) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail_screen, container, false)
    }
}

