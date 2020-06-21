package com.iceka.whatsappclonekotlin.data.model

import com.google.firebase.firestore.Exclude

data class User(
    val uid: String?,
    val username: String?,
    val isNew: Boolean,
    val phone: String?,
    val photoUrl: String,
    val isOnline: Boolean,
    val lastSeen: Long?,
    val about: String?
)