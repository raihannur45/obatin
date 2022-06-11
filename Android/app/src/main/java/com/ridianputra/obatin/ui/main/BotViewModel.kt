package com.ridianputra.obatin.ui.main

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ridianputra.obatin.data.Repository

class BotViewModel(private val repository: Repository) : ViewModel() {

    fun robot(token: String, body: JsonObject) = repository.robot(token, body)

    fun robotMessage(token: String, body: JsonObject) = repository.robotMessage(token, body)

    fun robotMessageReply(token: String, body: JsonObject) =
        repository.robotMessageReply(token, body)

    fun updateAccessToken(body: JsonObject) = repository.updateAccessToken(body)

    fun deleteAccessToken(body: JsonObject) = repository.deleteAccessToken(body)
}