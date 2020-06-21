package com.iceka.whatsappclonekotlin.ui.profile

import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.iceka.whatsappclonekotlin.data.model.User
import com.iceka.whatsappclonekotlin.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel @ViewModelInject constructor(private val userRepository: UserRepository): ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val currentUser = firebaseAuth.currentUser!!

    val dataImageUri = MutableLiveData<Uri>()

    init {
        Log.i("MYTAG", "phone nya ${firebaseAuth.currentUser?.phoneNumber}")
        checkPhoneNumber()
    }


    private fun checkPhoneNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.checkIfPhoneNumberAlreadyRegistered(currentUser.phoneNumber)

            if (response.isEmpty) {
                Log.i("MYTAG", "no data")
            } else {
                Log.i("MYTAG", "ada cuk")
            }
        }
    }


}