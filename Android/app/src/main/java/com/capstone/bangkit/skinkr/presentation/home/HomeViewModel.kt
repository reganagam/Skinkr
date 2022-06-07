package com.capstone.bangkit.skinkr.presentation.home

import androidx.lifecycle.ViewModel
import com.capstone.bangkit.skinkr.data.repository.AcneRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class HomeViewModel(private val mAcneRepository: AcneRepository) : ViewModel(){
    fun uploadAndScan(photo: File) = mAcneRepository.uploadAndScan(photo)
}