package com.molette.uniq.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.molette.uniq.R
import com.molette.uniq.databinding.CharacterCellBinding
import com.molette.uniq.databinding.FragmentHomeBinding
import com.molette.uniq.presentation.home.adapters.CharacterAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: CharacterAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CharacterAdapter()
        loadCharacters()
        initLoad()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.characterRV.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadCharacters(){
        lifecycleScope.launch {
            homeViewModel.getCharactersPaged()?.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initLoad(){

    }
}