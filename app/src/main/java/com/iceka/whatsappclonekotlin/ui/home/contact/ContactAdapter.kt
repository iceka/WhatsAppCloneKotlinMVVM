package com.iceka.whatsappclonekotlin.ui.home.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iceka.whatsappclonekotlin.data.model.User
import com.iceka.whatsappclonekotlin.databinding.ItemContactBinding

class ContactAdapter(private val clickListener: ContactListener): ListAdapter<User, ContactAdapter.MyViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.phone == newItem.phone
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    inner class MyViewHolder(val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: ContactListener) {
            binding.clickListener = clickListener
            binding.user = user
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, clickListener)
    }

    class ContactListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }
}