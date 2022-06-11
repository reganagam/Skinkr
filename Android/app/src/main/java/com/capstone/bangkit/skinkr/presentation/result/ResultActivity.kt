package com.capstone.bangkit.skinkr.presentation.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.capstone.bangkit.skinkr.R
import com.capstone.bangkit.skinkr.data.remote.responses.AcneResult
import com.capstone.bangkit.skinkr.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    val resAcne =MutableLiveData<AcneResult>()

    private lateinit var binding: ActivityResultBinding

    private lateinit var userCurrentPhoto: String
    private lateinit var acneName: String
    private lateinit var acneDesc: String

    companion object {
        val ACNE_NAME = "ACNE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivAcne.setImageResource(userCurrentPhoto.toInt())
        binding.tvAcneName.text = acneName
        binding.tvAcneDesc.text = acneDesc
    }
}