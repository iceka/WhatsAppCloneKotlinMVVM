package com.iceka.whatsappclonekotlin.ui.auth.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iceka.whatsappclonekotlin.data.repositories.UtilRepository

@Suppress("UNCHECKED_CAST")
class CountryViewModelFactory(private val utilRepository: UtilRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(utilRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}