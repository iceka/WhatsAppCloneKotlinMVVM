package com.iceka.whatsappclonekotlin.data.firebase

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.iceka.whatsappclonekotlin.data.model.User
import kotlinx.coroutines.tasks.await

class FirebaseSourceUser {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }


    suspend fun signInWithPhoneCredential(credential: PhoneAuthCredential): AuthResult {
        return firebaseAuth.signInWithCredential(credential).await()
    }

    suspend fun getUser(phoneNumber: String?): QuerySnapshot {
        return db.collection("users")
            .whereEqualTo("phone", phoneNumber)
            .limit(1)
            .get()
            .await()
    }

    suspend fun createUser(user: User, uid: String): Boolean {
        db.collection("users")
            .document(uid)
            .set(user)
            .await()
        return true
    }

    suspend fun checkIfPhoneNumberAlreadyRegistered(phoneNumber: String?): QuerySnapshot {
        return db.collection("users")
            .whereEqualTo("phone", phoneNumber)
            .limit(1)
            .get().await()
    }

    fun uploadAvatar(uid: String, imageUri: Uri) {
        storage.reference
            .child("avatar")
            .child(uid)
            .child(imageUri.lastPathSegment!!)
            .putFile(imageUri)
    }

    fun createNewUser(user: User) {
        db.collection("users")
            .add(user)

    }

}

