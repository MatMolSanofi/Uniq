package com.molette.uniq.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.molette.uniq.R
import com.molette.uniq.databinding.CharacterCellBinding
import com.molette.uniq.databinding.ContactCellBinding
import com.molette.uniq.presentation.models.Contact

class ContactAdapter(private val navController: NavController): RecyclerView.Adapter<ContactViewHolder>() {

    var data: List<Contact> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ContactCellBinding>(layoutInflater, R.layout.contact_cell, parent, false)

        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class ContactViewHolder(val binding: ContactCellBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(contact: Contact){
        binding.contact = contact
    }
}