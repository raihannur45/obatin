package com.ridianputra.obatin.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TokenViewModelFactory(private val dataStore: TokenDataStore) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(TokenViewModel::class.java)) {
            TokenViewModel(dataStore) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
}