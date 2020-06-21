package com.iceka.whatsappclonekotlin.data.repository

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.iceka.whatsappclonekotlin.data.firebase.FirebaseSourceUser
import com.iceka.whatsappclonekotlin.data.model.User
import javax.inject.Inject


class UserRepository @Inject constructor(private val firebase: FirebaseSourceUser) {

    suspend fun signInWithPhoneCredential(credential: PhoneAuthCredential) = firebase.signInWithPhoneCredential(credential)

    suspend fun getUser(phoneNumber: String?) = firebase.getUser(phoneNumber)

    suspend fun createUser(user: User, uid: String) = firebase.createUser(user, uid)

    suspend fun checkIfPhoneNumberAlreadyRegistered(phoneNumber: String?) = firebase.checkIfPhoneNumberAlreadyRegistered(phoneNumber)

    fun uploadImage(uid: String, imageUri: Uri) = firebase.uploadAvatar(uid, imageUri)
}