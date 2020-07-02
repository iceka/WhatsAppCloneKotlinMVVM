package com.iceka.whatsappclonekotlin.data.repositories

import com.iceka.whatsappclonekotlin.data.firebase.FirebaseSourceChat
import com.iceka.whatsappclonekotlin.data.model.Chat
import com.iceka.whatsappclonekotlin.data.model.Conversation
import javax.inject.Inject

class ChatRepository @Inject constructor(private val firebase: FirebaseSourceChat) {

    fun getConversation(uid: String) = firebase.getConversation(uid)

    suspend fun sendConversationAsSender(senderId: String, receiverId: String, conversation: Conversation) = firebase.sendConversationAsSender(senderId, receiverId, conversation)

    suspend fun sendConversationToReceiver(senderId: String, receiverId: String, conversation: Conversation) = firebase.sendConversationToReceiver(senderId, receiverId, conversation)

}