package com.iceka.whatsappclonekotlin.data.model

data class Chat(
    val message: String,
    val timestamp: Long,
    val senderId: String,
    val receiverId: String
)