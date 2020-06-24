package com.iceka.whatsappclonekotlin.ui.home.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.iceka.whatsappclonekotlin.data.model.Conversation
import com.iceka.whatsappclonekotlin.data.repositories.ChatRepository
import com.iceka.whatsappclonekotlin.utils.Resource
import kotlinx.coroutines.launch

class ConversationViewModel(private val chatRepository: ChatRepository): ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val _conversationList = MutableLiveData<Resource<List<Conversation>>>()
    val conversationList: LiveData<Resource<List<Conversation>>>
        get() = _conversationList


    fun getConversation() {
        viewModelScope.launch {

            val result = chatRepository.getConversation(firebaseAuth.currentUser!!.uid).toObjects<Conversation>()
            _conversationList.value
        }
    }
}