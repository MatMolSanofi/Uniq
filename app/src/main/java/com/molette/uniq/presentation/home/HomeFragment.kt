package com.molette.uniq.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ConcatAdapter
import com.molette.uniq.R
import com.molette.uniq.databinding.FragmentHomeBinding
import com.molette.uniq.presentation.home.adapters.CharacterAdapter
import com.molette.uniq.presentation.home.adapters.ContactAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterAdapter = CharacterAdapter(findNavController())
        contactAdapter = ContactAdapter(findNavController())

        askReadContactsPermission()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.characterRV.adapter = ConcatAdapter(contactAdapter, characterAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCharacters()
        loadContacts()
    }
    
    private fun askReadContactsPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CONTACTS_PERMISSION);
        }
    }

    private fun loadCharacters(){
        lifecycleScope.launch {
            homeViewModel.getCharactersPaged()?.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun loadContacts(){
        homeViewModel.contacts.observe(viewLifecycleOwner, Observer {
            contactAdapter.data = it
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_CONTACTS_PERMISSION && grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED){
            homeViewModel.getContacts()
        }
    }

    companion object {
        const val REQUEST_CONTACTS_PERMISSION = 0
    }
}