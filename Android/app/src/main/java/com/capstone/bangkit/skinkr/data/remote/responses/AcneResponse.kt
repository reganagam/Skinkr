package com.capstone.bangkit.skinkr.data.remote.responses

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
