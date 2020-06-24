package com.iceka.whatsappclonekotlin.ui.home.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iceka.whatsappclonekotlin.R
import com.iceka.whatsappclonekotlin.databinding.FragmentChatTabBinding

class ChatTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatTabBinding.inflate(inflater, container, false)



        return binding.root
    }

}