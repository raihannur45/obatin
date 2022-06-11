package com.ridianputra.obatin.data.network

import com.google.gson.JsonObject
import com.ridianputra.obatin.data.network.response.*
import retrofit2.http.*

interface ApiService {

    @POST("/bot")
    suspend fun robot(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): RobotResponse

    @POST("/bot/message")
    suspend fun robotMessage(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): MessageResponse

    @POST("/bot/message/reply")
    suspend fun robotMessageReply(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): ReplyResponse

    @POST("/users")
    suspend fun userRegister(
        @Body body: JsonObject
    ): RegisterResponse

    @POST("/auth")
    suspend fun userLogin(
        @Body body: JsonObject
    ): LoginResponse

    @GET("/users/profiles")
    suspend fun getUserData(
        @Header("Authorization") token: String,
        @Query("email") email: String,
        @Query("username") username: String
    ): GetUserProfileResponse

    @PUT("/auth")
    suspend fun updateAccessToken(
        @Body body: JsonObject
    ): UpdateResponse

    @HTTP(method = "DELETE", path = "/auth", hasBody = true)
    suspend fun deleteAccessToken(
        @Body body: JsonObject
    ): DeleteResponse
}