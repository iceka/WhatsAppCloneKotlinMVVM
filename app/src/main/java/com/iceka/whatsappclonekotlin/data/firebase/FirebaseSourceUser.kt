package com.iceka.whatsappclonekotlin.data.firebase

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.iceka.whatsappclonekotlin.data.model.User
import kotlinx.coroutines.tasks.await

class FirebaseSourceUser {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val fire = Firebase.firestore
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

    suspend fun getUserList(): QuerySnapshot {
        return db.collection("users")
            .get()
            .await()
    }

    suspend fun createUser(user: User, uid: String){
        db.collection("users")
            .document(uid)
            .set(user)
            .await()
    }

    suspend fun loadExistedUser(uid: String): QuerySnapshot {
        return fire.collection("users")
            .whereEqualTo(FieldPath.documentId(), uid)
            .limit(1)
            .get()
            .await()
    }

    suspend fun checkIfPhoneNumberAlreadyRegistered(phoneNumber: String?): QuerySnapshot {
        return db.collection("users")
            .whereEqualTo("phone", phoneNumber)
            .limit(1)
            .get().await()
    }

    suspend fun uploadAvatar(uid: String, imageUri: Uri): UploadTask.TaskSnapshot {
        return storage.reference
            .child("avatar")
            .child(uid)
            .child(imageUri.lastPathSegment!!)
            .putFile(imageUri)
            .await()
    }

    suspend fun getReceiverUidByPhoneNumber(phoneNumber: String): QuerySnapshot {
        return db.collection("users")
            .whereEqualTo("phone", phoneNumber)
            .limit(1)
            .get()
            .await()
    }

}

