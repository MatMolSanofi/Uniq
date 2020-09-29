package com.molette.uniq.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.molette.uniq.R
import com.molette.uniq.databinding.FragmentDetailsBinding
import com.molette.uniq.databinding.FragmentHomeBinding
import com.molette.uniq.presentation.home.HomeViewModel
import com.molette.uniq.presentation.home.adapters.CharacterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel by viewModel<DetailsViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewModel.character.observe(viewLifecycleOwner, Observer {
            binding.character = it
            Glide.with(requireContext())
                .load(it.thumbnail+"/landscape_xlarge."+it.extension)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.detailsImage)
        })
    }

    override fun onResume() {
        super.onResume()
        detailsViewModel.characterId.value = args.characterId
    }
}