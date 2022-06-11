package com.ridianputra.obatin.data.network.response

import com.google.gson.annotations.SerializedName

data class ReplyResponse(

	@field:SerializedName("data")
	val data: DataReply,

	@field:SerializedName("status")
	val status: String
)

data class ResponseReply(

	@field:SerializedName("jawab")
	val jawab: String
)

data class DataReply(

	@field:SerializedName("response")
	val response: ResponseReply
)
