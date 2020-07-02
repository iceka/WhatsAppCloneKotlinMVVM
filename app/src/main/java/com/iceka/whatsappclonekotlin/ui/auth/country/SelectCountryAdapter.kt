package com.iceka.whatsappclonekotlin.ui.auth.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iceka.whatsappclonekotlin.R
import com.iceka.whatsappclonekotlin.data.model.CountryCallingCodes
import com.iceka.whatsappclonekotlin.databinding.ItemCountryCallingCodeBinding
import com.iceka.whatsappclonekotlin.ui.home.chat.chatroom.ChatViewModel

class SelectCountryAdapter(private val clickListener: CountryListener): ListAdapter<CountryCallingCodes, SelectCountryAdapter.MyViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<CountryCallingCodes>() {
        override fun areItemsTheSame(
            oldItem: CountryCallingCodes,
            newItem: CountryCallingCodes
        ): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(
            oldItem: CountryCallingCodes,
            newItem: CountryCallingCodes
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class MyViewHolder(private val binding: ItemCountryCallingCodeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryCallingCodes, clickListener: CountryListener) {
            binding.country = country
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemCountryCallingCodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val country = getItem(position)
        holder.bind(country, clickListener)
    }

    class CountryListener(val clickListener: (country: CountryCallingCodes) -> Unit) {
        fun onClick(country: CountryCallingCodes) = clickListener(country)
    }
}