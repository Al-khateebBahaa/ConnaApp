package com.bsa.conna.model.response

import com.google.gson.annotations.SerializedName

class BaseResponse {

    @SerializedName("message")
    val message: String? = null
    @SerializedName("cod")
    val status: Int? = null



}

