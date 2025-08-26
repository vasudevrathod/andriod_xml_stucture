package com.vaasudev.androidstructure.domain.response

import com.google.gson.annotations.SerializedName

data class InitResponse(
    override val status: Boolean,
    override val message: String,
    val state: Int,

): BaseResponseInterface