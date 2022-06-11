package com.ridianputra.obatin.data.network.response

import com.google.gson.annotations.SerializedName

data class UpdateResponse(

	@field:SerializedName("data")
	val data: UpdateData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class UpdateData(

	@field:SerializedName("accessToken")
	val accessToken: String
)