package com.example.ourstoryapps.data.model

import com.google.gson.annotations.SerializedName

data class ResponseStoryUp(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
