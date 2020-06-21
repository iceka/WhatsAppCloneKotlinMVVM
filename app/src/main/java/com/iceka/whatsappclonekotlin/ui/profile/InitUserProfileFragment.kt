package com.iceka.whatsappclonekotlin.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iceka.whatsappclonekotlin.R
import com.iceka.whatsappclonekotlin.databinding.FragmentInitUserProfileBinding
import com.iceka.whatsappclonekotlin.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

const val RC_PHOTO_PICKER = 200

@AndroidEntryPoint
class InitUserProfileFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentInitUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInitUserProfileBinding.inflate(inflater, container, false)

        binding.viewModel = userViewModel
        binding.lifecycleOwner = this


        binding.imgAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER)
        }

        binding.btNext.setOnClickListener {
            authViewModel.logout()
        }

        userViewModel.dataImageUri.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG", "hasil observe ${it.hashCode()}")
        })

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val imageUri = data.data
                userViewModel.dataImageUri.value = imageUri
            }
        }
    }

}