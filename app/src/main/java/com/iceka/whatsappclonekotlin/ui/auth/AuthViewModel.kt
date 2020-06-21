package com.iceka.whatsappclonekotlin.ui.auth

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.iceka.whatsappclonekotlin.data.model.CountryCallingCodes
import com.iceka.whatsappclonekotlin.data.repository.UserRepository
import com.iceka.whatsappclonekotlin.data.repository.UtilRepository
import com.iceka.whatsappclonekotlin.utils.Event
import com.iceka.whatsappclonekotlin.utils.Resource
import com.iceka.whatsappclonekotlin.utils.formatDialCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit

class AuthViewModel @ViewModelInject constructor(
    private val application: Application,
    private val utilRepository: UtilRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val countryDialCode = MutableLiveData<String>()
    val countryName = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()

    private val verificationIds = MutableLiveData<String>()
    private var resendingToken: PhoneAuthProvider.ForceResendingToken? = null

    private val _isAuthenticate = MutableLiveData<Boolean>()
    val isAuthenticate: LiveData<Boolean>
        get() = _isAuthenticate

    private val _numberIsCorrect = MutableLiveData<Boolean>()
    val numberIsCorrect = _numberIsCorrect

    private val _otpCode = MutableLiveData<String>()
    val otpCode: LiveData<String>
        get() = _otpCode

    private val _navigateToInitProfile = MutableLiveData<Event<Boolean>>()
    val navigateToInitProfile: LiveData<Event<Boolean>>
        get() = _navigateToInitProfile

    private val _response = MutableLiveData<Resource<Any>>()
    val response: LiveData<Resource<Any>>
        get() = _response

    var number = MutableLiveData<String>()


    fun checkAuth() {
        _isAuthenticate.value = firebaseAuth.currentUser != null
    }

    fun logout() {
        firebaseAuth.signOut()
        _isAuthenticate.value = false
    }

    private fun getDefaultTelephonyManager(): String {
        var code = ""
        try {
            val telephonyManager: TelephonyManager =
                application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry: String = telephonyManager.simCountryIso
            if (simCountry.length == 2) {
                code = simCountry
            } else if (telephonyManager.phoneType != TelephonyManager.PHONE_TYPE_CDMA) {
                val networkCountry = telephonyManager.networkCountryIso
                if (networkCountry != null && networkCountry.length == 2) {
                    code = networkCountry
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return code.toUpperCase(Locale.US)
    }

    fun getCountryCodes() {
        viewModelScope.launch {
            val result = utilRepository.getCountryCodes(getDefaultTelephonyManager())
                .toObjects(CountryCallingCodes::class.java)
            for (res in result) {
                if (countryDialCode.value == null) {
                    val formattedDialCode = formatDialCode(res.dial_code)
                    countryName.value = res.name
                    countryDialCode.value = formattedDialCode
                }
            }
        }
    }

    fun checkPhoneNumber() {
        number.value = "+${countryDialCode.value}${phoneNumber.value}"
        when {
            number.value!!.length < 11 -> {
                _numberIsCorrect.value = false
            }
            else -> {
                _numberIsCorrect.value = true
            }
        }
    }

    fun sendVerificationCode(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallback
        )
    }

    private val mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            verificationIds.value = verificationId
            resendingToken = token
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            val code = phoneAuthCredential.smsCode
            if (code != null) {
                _otpCode.value = code
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            _response.postValue(Resource.Error(message = e.message))
        }

    }

    fun verifyCode(code: String) {
        try {
            val credential = PhoneAuthProvider.getCredential(verificationIds.value!!, code)
            signInWithPhoneCredential(credential)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun signInWithPhoneCredential(credential: PhoneAuthCredential) {
        _response.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)
            try {
                userRepository.signInWithPhoneCredential(credential)
                withContext(Dispatchers.Main) {
                    navigateToInitUserProfile()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _response.postValue(Resource.Error(message = e.message))
            }

        }
    }

    private fun navigateToInitUserProfile() {
        _navigateToInitProfile.value = Event(true)
    }

}