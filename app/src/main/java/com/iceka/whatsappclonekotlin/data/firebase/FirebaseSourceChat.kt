package com.iceka.whatsappclonekotlin.data.firebase

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.iceka.whatsappclonekotlin.data.model.Chat
import kotlinx.coroutines.tasks.await

class FirebaseSourceChat {

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    suspend fun getConversation(uid: String): QuerySnapshot {
        return db.collection("conversation")
            .document(uid)
            .collection("conversations")
            .get()
            .await()
    }

    suspend fun sendConversationAsSender(senderId: String, receiverId: String, chat: Chat) {
        db.collection("conversation")
            .document(senderId)
            .collection("conversationsWith")
            .document(receiverId)
            .set(chat)
            .await()
    }

    suspend fun sendConversationToReceiver(senderId: String, receiverId: String, chat: Chat) {
        db.collection("conversation")
            .document(receiverId)
            .collection("conversationsWith")
            .document(senderId)
            .set(chat)
            .await()
    }

}