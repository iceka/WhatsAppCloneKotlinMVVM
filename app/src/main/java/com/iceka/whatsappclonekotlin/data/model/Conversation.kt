package com.iceka.whatsappclonekotlin.data.model

data class Conversation(
    val chatWithId: String = "",
    val lastMessage: String = "",
    val timestamp: Long = 0L,
    val unreadChatCount: Int = 0
)