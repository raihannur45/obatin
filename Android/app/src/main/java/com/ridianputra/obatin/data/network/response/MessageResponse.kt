package com.ridianputra.obatin.data.network.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(

	@field:SerializedName("data")
	val data: DataMessage,

	@field:SerializedName("status")
	val status: String
)

data class DataMessage(

	@field:SerializedName("response")
	val response: ResponseMessage
)

data class ResponseMessage(

	@field:SerializedName("jawab")
	val jawab: String
)
