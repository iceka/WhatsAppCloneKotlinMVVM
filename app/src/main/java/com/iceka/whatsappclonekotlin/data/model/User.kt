package com.iceka.whatsappclonekotlin.data.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username: String? = "",
    val phone: String? = "",
    val photoUrl: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0L,
    val about: String = ""
): Parcelable