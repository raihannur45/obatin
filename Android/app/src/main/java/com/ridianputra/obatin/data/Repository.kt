package com.ridianputra.obatin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.JsonObject
import com.ridianputra.obatin.data.network.ApiService
import com.ridianputra.obatin.data.network.response.*

class Repository(private val apiService: ApiService) {

    fun robot(token: String, body: JsonObject): LiveData<Result<ResponseRobot>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.robot("Bearer $token", body)
                emit(Result.Success(response.data.response))
            } catch (e: Exception) {
                Log.e("Repository", "robot: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun robotMessage(token: String, body: JsonObject): LiveData<Result<ResponseMessage>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.robotMessage("Bearer $token", body)
                emit(Result.Success(response.data.response))
            } catch (e: Exception) {
                Log.e("Repository", "robotMessage: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun robotMessageReply(token: String, body: JsonObject): LiveData<Result<ResponseReply>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.robotMessageReply("Bearer $token", body)
                emit(Result.Success(response.data.response))
            } catch (e: Exception) {
                Log.e("Repository", "robotMessageReply: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun userRegister(body: JsonObject): LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.userRegister(body)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.e("Repository", "userRegister: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun userLogin(body: JsonObject): LiveData<Result<LoginData>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.userLogin(body)
                emit(Result.Success(response.data))
            } catch (e: Exception) {
                Log.e("Repository", "userLogin: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getUserData(token: String, email: String, username: String): LiveData<Result<UserProfile>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getUserData("Bearer $token", email, username)
                emit(Result.Success(response.data))
            } catch (e: Exception) {
                Log.e("Repository", "getUserData: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun updateAccessToken(body: JsonObject): LiveData<Result<UpdateData>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.updateAccessToken(body)
                emit(Result.Success(response.data))
            } catch (e: Exception) {
                Log.e("Repository", "updateAccessToken: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun deleteAccessToken(body: JsonObject): LiveData<Result<DeleteResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.deleteAccessToken(body)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.e("Repository", "deleteAccessToken: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService)
        }.also { instance = it }
    }
}