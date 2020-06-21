package com.iceka.whatsappclonekotlin.data.repository

import com.google.firebase.firestore.QuerySnapshot
import com.iceka.whatsappclonekotlin.data.firebase.FirebaseSourceUtil
import javax.inject.Inject

class UtilRepository @Inject constructor(private val firebase: FirebaseSourceUtil) {

    suspend fun getCountryCodes(code: String?): QuerySnapshot = firebase.getCountryCodes(code)

    suspend fun getCountryList(): QuerySnapshot = firebase.getCountryList()
}