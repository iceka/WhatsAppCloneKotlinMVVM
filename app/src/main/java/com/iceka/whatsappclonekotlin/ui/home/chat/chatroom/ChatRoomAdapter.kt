package com.iceka.whatsappclonekotlin.ui.home.chat.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.iceka.whatsappclonekotlin.data.model.Chat
import com.iceka.whatsappclonekotlin.data.model.User
import com.iceka.whatsappclonekotlin.databinding.ItemChatIncomingBinding
import com.iceka.whatsappclonekotlin.databinding.ItemChatOutgoingBinding

private const val ITEM_VIEW_TYPE_INCOMING = 0
private const val ITEM_VIEW_TYPE_OUTGOING = 1

class ChatRoomAdapter : ListAdapter<Chat, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }

    class OutgoingViewHolder(val binding: ItemChatOutgoingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OutgoingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChatOutgoingBinding.inflate(layoutInflater, parent, false)
                return OutgoingViewHolder(binding)
            }
        }
    }

    class IncomingViewHolder(val binding: ItemChatIncomingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): IncomingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChatIncomingBinding.inflate(layoutInflater, parent, false)
                return IncomingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chat = getItem(position)
        return if (isSender(chat)) {
            ITEM_VIEW_TYPE_OUTGOING
        } else {
            ITEM_VIEW_TYPE_INCOMING
        }
    }

    private fun isSender(chat: Chat): Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.uid.equals(chat.senderId, ignoreCase = true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_INCOMING -> IncomingViewHolder.from(parent)
            ITEM_VIEW_TYPE_OUTGOING -> OutgoingViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = getItem(position)
        when(holder) {
            is IncomingViewHolder -> holder.bind()
            is OutgoingViewHolder -> holder.bind()
        }
    }

    class ChatListener(val clickListener: (chat: Chat) -> Unit) {
        fun onClick(chat: Chat) = clickListener(chat)
    }

}