package com.capstone.bangkit.skinkr.data.remote.responses

import com.google.gson.annotations.SerializedName

data class AcneResponse(
    @field:SerializedName("prediction")
    val prediction: String
/*
    @field:SerializedName("products")
    val products: String*/
)
