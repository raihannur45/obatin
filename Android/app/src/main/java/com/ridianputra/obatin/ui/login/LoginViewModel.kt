package com.ridianputra.obatin.ui.login

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ridianputra.obatin.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun userLogin(body: JsonObject) = repository.userLogin(body)

    fun getUserData(token: String, email: String, username: String) =
        repository.getUserData(token, email, username)
}