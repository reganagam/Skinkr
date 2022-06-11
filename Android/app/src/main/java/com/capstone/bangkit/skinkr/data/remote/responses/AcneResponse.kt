package com.capstone.bangkit.skinkr.data.remote.responses

import com.google.gson.annotations.SerializedName

data class AcneResponse(
    @SerializedName("jenis")
    var acneName: String,

    @SerializedName("deskripsi")
    var acneDesc: String,

    @SerializedName("solusi")
    val acneSolusi: ArrayList<String>,

    @SerializedName("saran")
    val acneSaran: ArrayList<String>
)
