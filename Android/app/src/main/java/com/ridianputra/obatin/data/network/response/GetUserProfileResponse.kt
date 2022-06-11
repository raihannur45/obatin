package com.ridianputra.obatin.data.network.response

import com.google.gson.annotations.SerializedName

data class GetUserProfileResponse(

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("data")
	val data: UserProfile
)

data class UserProfile(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("email")
	val email: String
)
