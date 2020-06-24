package com.iceka.whatsappclonekotlin.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iceka.whatsappclonekotlin.databinding.FragmentWelcomeBinding
import com.iceka.whatsappclonekotlin.ui.HomeActivity
import com.iceka.whatsappclonekotlin.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        viewModel.checkAuth()

        viewModel.getCountryCodes()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val isRegistered = sharedPref?.getBoolean("isRegistered", false)

        viewModel.isAuthenticate.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                if (isRegistered == true) {
                    startActivity(Intent(context, HomeActivity::class.java))
                    activity?.finish()
                } else {
                    findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToInitUserProfileFragment())
                }
            }
        })

        binding.btnAgreeTos.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToInputPhoneNumberFragment())
        }

        return binding.root
    }
}