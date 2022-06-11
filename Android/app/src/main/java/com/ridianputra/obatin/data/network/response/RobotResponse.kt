package com.ridianputra.obatin.data.network.response

import com.google.gson.annotations.SerializedName

data class RobotResponse(

	@field:SerializedName("data")
	val data: DataRobot,

	@field:SerializedName("status")
	val status: String
)

data class ResponseRobot(

	@field:SerializedName("namaobat")
	val namaobat: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataRobot(

	@field:SerializedName("response")
	val response: ResponseRobot
)
