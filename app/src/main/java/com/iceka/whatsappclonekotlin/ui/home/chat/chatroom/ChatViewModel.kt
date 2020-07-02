package com.iceka.whatsappclonekotlin.ui.home.chat.chatroom

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.iceka.whatsappclonekotlin.data.model.Chat
import com.iceka.whatsappclonekotlin.data.model.Conversation
import com.iceka.whatsappclonekotlin.data.model.User
import com.iceka.whatsappclonekotlin.data.repositories.ChatRepository
import com.iceka.whatsappclonekotlin.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ChatViewModel @ViewModelInject constructor(private val chatRepository: ChatRepository) :
    ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val message = MutableLiveData<String>()

    private val currentUser = firebaseAuth.currentUser!!

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData

    private val _conversationList = MutableLiveData<Resource<List<Conversation>>>()
    val conversationList: LiveData<Resource<List<Conversation>>>
        get() = _conversationList

    init {
        userData.value?.username
        getConversation()
    }

    fun setUserData(user: User?) {
        _userData.value = user
    }

    fun getReceiverUid() {

    }

    fun sendConversation(receiverUid: String) {
        val conversation = Conversation(
            receiverUid,
            message.value!!,
            System.currentTimeMillis()
        )
        viewModelScope.launch {
            async {
                try {
                    chatRepository.sendConversationAsSender(
                        currentUser.uid,
                        receiverUid,
                        conversation
                    )
                    chatRepository.sendConversationToReceiver(
                        currentUser.uid,
                        receiverUid,
                        conversation
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    private fun getConversation() {
        _conversationList.value = Resource.Loading()
        chatRepository.getConversation(firebaseAuth.currentUser!!.uid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.i("MYTAG", "Ada yg error ${firebaseFirestoreException.message}")
                    _conversationList.postValue(Resource.Error(message = firebaseFirestoreException.message))
                }

                if (querySnapshot != null) {
                    val cek = querySnapshot.toObjects<Conversation>()
                    _conversationList.value = Resource.Success(cek)

                }
            }
    }


}