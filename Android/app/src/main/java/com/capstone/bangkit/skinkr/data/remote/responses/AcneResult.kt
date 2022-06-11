package com.capstone.bangkit.skinkr.data.remote.responses

import com.google.gson.annotations.SerializedName

data class AcneResult(
    @SerializedName("jenis")
    var acneName: String?,

    @SerializedName("deskripsi")
    var acneDesc: String?,

    @SerializedName("saran")
    val produk:ArrayList<AcneResultProducts>
)
