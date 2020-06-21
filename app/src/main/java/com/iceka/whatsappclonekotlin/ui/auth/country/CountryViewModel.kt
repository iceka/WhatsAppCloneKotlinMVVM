package com.iceka.whatsappclonekotlin.ui.auth.country

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObjects
import com.iceka.whatsappclonekotlin.data.model.CountryCallingCodes
import com.iceka.whatsappclonekotlin.data.repository.UtilRepository
import com.iceka.whatsappclonekotlin.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryViewModel @ViewModelInject constructor(private val utilRepository: UtilRepository) : ViewModel() {

    private val _countryList = MutableLiveData<Resource<List<CountryCallingCodes>>>()
    val countryList: LiveData<Resource<List<CountryCallingCodes>>>
        get() = _countryList

    init {
        getCountryList()
    }

    private fun getCountryList() {
        _countryList.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = utilRepository.getCountryList()
                    .toObjects<CountryCallingCodes>()
                _countryList.postValue(Resource.Success(result))
            } catch (e: Exception) {
                e.printStackTrace()
                _countryList.postValue(Resource.Error(message =  e.message))
            }
        }
    }
}