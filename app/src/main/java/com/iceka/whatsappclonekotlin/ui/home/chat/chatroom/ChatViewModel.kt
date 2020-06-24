package com.iceka.whatsappclonekotlin.ui.home.chat.chatroom

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.iceka.whatsappclonekotlin.data.model.Chat
import com.iceka.whatsappclonekotlin.data.model.User
import com.iceka.whatsappclonekotlin.data.repositories.ChatRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ChatViewModel @ViewModelInject constructor(private val chatRepository: ChatRepository): ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val message = MutableLiveData<String>()

    private val currentUser = firebaseAuth.currentUser!!

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData

    init {
        userData.value?.username
    }

    fun setUserData(user: User?) {
        _userData.value = user
    }

    fun getReceiverUid() {

    }

    fun sendConversation(receiverUid: String) {
        val chat = Chat(
            message.value!!,
            System.currentTimeMillis(),
            currentUser.uid,
            receiverUid
        )
        viewModelScope.launch {
            async {
                try {
                    chatRepository.sendConversationAsSender(currentUser.uid, receiverUid, chat)
                    chatRepository.sendConversationToReceiver(currentUser.uid, receiverUid, chat)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }


}