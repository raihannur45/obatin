package com.ridianputra.obatin.ui.register

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ridianputra.obatin.data.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun userRegister(body: JsonObject) = repository.userRegister(body)
}