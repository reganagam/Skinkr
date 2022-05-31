package com.capstone.bangkit.skinkr.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.capstone.bangkit.skinkr.data.remote.responses.AcneResponse
import com.capstone.bangkit.skinkr.data.remote.services.ApiService
import com.capstone.bangkit.skinkr.presentation.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AcneRepository private constructor(private val apiService: ApiService){

    private val RRespond = MediatorLiveData<ResultRespond<AcneResponse>>()


    fun uploadAndScan(photo: File) : LiveData<ResultRespond<AcneResponse>>{

        val file = reduceFileImage(photo)

        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        RRespond.value = ResultRespond.Loading
        val client = apiService.uploadImage(imageMultipart)
        client.enqueue(object : Callback<AcneResponse> {
            override fun onResponse(
                call: Call<AcneResponse>,
                response: Response<AcneResponse>
            )  {
                if (response.isSuccessful){
                    RRespond.value = ResultRespond.Success(response.body()!!)
                }
            }
            override fun onFailure(call: Call<AcneResponse>, t: Throwable) {
                RRespond.value = ResultRespond.Error(t.message.toString())
            }
        })
        return RRespond
    }

    companion object {
        @Volatile
        private var instance: AcneRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AcneRepository =
            instance ?: synchronized(this) {
                instance ?: AcneRepository(apiService)
            }.also { instance = it }
    }
}