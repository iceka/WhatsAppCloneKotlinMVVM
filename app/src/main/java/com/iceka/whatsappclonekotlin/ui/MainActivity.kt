package com.iceka.whatsappclonekotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iceka.whatsappclonekotlin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var firebaseSourceUser: FirebaseSourceUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}