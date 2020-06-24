package com.iceka.whatsappclonekotlin.data.repositories

import com.iceka.whatsappclonekotlin.data.firebase.FirebaseSourceChat
import com.iceka.whatsappclonekotlin.data.model.Chat
import javax.inject.Inject

class ChatRepository @Inject constructor(private val firebase: FirebaseSourceChat) {

    suspend fun getConversation(uid: String) = firebase.getConversation(uid)

    suspend fun sendConversationAsSender(senderId: String, receiverId: String, chat: Chat) = firebase.sendConversationAsSender(senderId, receiverId, chat)

    suspend fun sendConversationToReceiver(senderId: String, receiverId: String, chat: Chat) = firebase.sendConversationToReceiver(senderId, receiverId, chat)

}