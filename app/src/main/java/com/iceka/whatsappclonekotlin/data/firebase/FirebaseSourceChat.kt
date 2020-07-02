package com.iceka.whatsappclonekotlin.data.firebase

import com.google.firebase.firestore.*
import com.iceka.whatsappclonekotlin.data.model.Chat
import com.iceka.whatsappclonekotlin.data.model.Conversation
import kotlinx.coroutines.tasks.await

class FirebaseSourceChat {

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

     fun getConversation(uid: String): Query {
        return db.collection("conversation")
            .document(uid)
            .collection("conversationsWith")
            .orderBy("timestamp", Query.Direction.DESCENDING)
    }

    suspend fun sendConversationAsSender(senderId: String, receiverId: String, conversation: Conversation) {
        db.collection("conversation")
            .document(senderId)
            .collection("conversationsWith")
            .document(receiverId)
            .set(conversation)
            .await()
    }

    suspend fun sendConversationToReceiver(senderId: String, receiverId: String, conversation: Conversation) {
        db.collection("conversation")
            .document(receiverId)
            .collection("conversationsWith")
            .document(senderId)
            .set(conversation)
            .await()
    }

}