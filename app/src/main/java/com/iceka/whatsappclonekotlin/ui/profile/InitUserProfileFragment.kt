package com.iceka.whatsappclonekotlin.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iceka.whatsappclonekotlin.R
import com.iceka.whatsappclonekotlin.databinding.FragmentInitUserProfileBinding
import com.iceka.whatsappclonekotlin.ui.HomeActivity
import com.iceka.whatsappclonekotlin.ui.auth.AuthViewModel
import com.iceka.whatsappclonekotlin.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

const val RC_PHOTO_PICKER = 200

@AndroidEntryPoint
class InitUserProfileFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
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
            startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                RC_PHOTO_PICKER
            )
        }

        binding.btNext.setOnClickListener {
            userViewModel.createUser()
            userViewModel.response.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading -> binding.loading.visibility = View.VISIBLE
                    is Resource.Success -> {
                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                        with (sharedPref!!.edit()) {
                            putBoolean("isRegistered", true)
                            commit()
                        }
                        binding.loading.visibility = View.GONE
                        Log.i("MYTAG", "berhasil register ${authViewModel.isRegistered.value}")
                        startActivity(Intent(context, HomeActivity::class.java))
                        activity?.finish()
                    }
                    is Resource.Error -> {
                        response.message?.let { exception ->
                            Toast.makeText(context, exception, Toast.LENGTH_SHORT).show()
                        }
                        binding.loading.visibility = View.GONE
                    }
                }
            })}



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