package com.iceka.whatsappclonekotlin.ui.home.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iceka.whatsappclonekotlin.data.model.Conversation
import com.iceka.whatsappclonekotlin.databinding.ItemConversationBinding

class ChatConversationAdapter(): ListAdapter<Conversation, ChatConversationAdapter.MyViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Conversation>() {
        override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem == newItem
        }
    }

    inner class MyViewHolder(val binding: ItemConversationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation) {
            binding.conversation = conversation
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val conversation = getItem(position)
        holder.bind(conversation)
    }

}