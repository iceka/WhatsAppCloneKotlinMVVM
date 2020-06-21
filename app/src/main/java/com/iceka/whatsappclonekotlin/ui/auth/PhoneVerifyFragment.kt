package com.iceka.whatsappclonekotlin.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iceka.whatsappclonekotlin.databinding.FragmentPhoneVerifyBinding
import com.iceka.whatsappclonekotlin.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneVerifyFragment : Fragment() {

    private lateinit var binding: FragmentPhoneVerifyBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneVerifyBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val arguments = PhoneVerifyFragmentArgs.fromBundle(requireArguments())

        viewModel.sendVerificationCode(arguments.phoneNumber)

        binding.btNext.setOnClickListener {
            viewModel.verifyCode(binding.etVerificationCode.text.toString())
        }

        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.loading.visibility = View.GONE
                    response.message?.let { exception ->
                        Toast.makeText(context, exception, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        viewModel.navigateToInitProfile.observe(viewLifecycleOwner, Observer {
            it.getEventNotHandled()?.let {
                findNavController().navigate(PhoneVerifyFragmentDirections.actionPhoneVerifyFragmentToInitUserProfileFragment())
            }
        })

        return binding.root
    }

}