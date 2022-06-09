package com.capstone.bangkit.skinkr.data.remote.services

import com.capstone.bangkit.skinkr.data.remote.responses.AcneResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("/")
    fun uploadImage(
        @Body byte64image : RequestBody
    ): Call<AcneResponse>
}