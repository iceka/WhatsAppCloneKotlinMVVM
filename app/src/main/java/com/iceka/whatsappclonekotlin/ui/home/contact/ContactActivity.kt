package com.iceka.whatsappclonekotlin.ui.home.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.iceka.whatsappclonekotlin.R
import com.iceka.whatsappclonekotlin.databinding.ActivityContactBinding
import com.iceka.whatsappclonekotlin.ui.home.chat.chatroom.ChatRoomActivity
import com.iceka.whatsappclonekotlin.ui.profile.UserViewModel
import com.iceka.whatsappclonekotlin.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact)

        viewModel.getContact()
        val contactAdapter = ContactAdapter(ContactAdapter.ContactListener {
            Log.i("MYTAG", "clickced")
            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("user", it)
            startActivity(intent)
        })

        viewModel.contactResponse.observe(this, Observer { response ->
            when(response) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    response.data?.let { userList ->
                        Log.i("MYTAG", "contact $userList")
                        contactAdapter.submitList(userList)
                        binding.rvContact.adapter = contactAdapter
                    }
                }
                is Resource.Error -> {}
            }
        })
    }
}