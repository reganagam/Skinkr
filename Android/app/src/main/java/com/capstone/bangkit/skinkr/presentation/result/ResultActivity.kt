package com.capstone.bangkit.skinkr.presentation.result

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.capstone.bangkit.skinkr.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private lateinit var userCurrentPhoto: String
    private lateinit var acneName: String
    private lateinit var acneDesc: String
    private lateinit var acneSol: String
    private lateinit var acneSaran: String
    private lateinit var acneImage: Bitmap

    companion object {
        const val ACNE_NAME = "acne_name"
        const val ACNE_DESC = "acne_desc"
        const val ACNE_SOL = "acne_sol"
        const val ACNE_SARAN = "acne_saran"
        const val USED_IMAGE = "used_image"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        acneName = intent.getStringExtra(ACNE_NAME).toString()
        acneDesc = intent.getStringExtra(ACNE_DESC).toString()
        acneSol = intent.getStringExtra(ACNE_SOL).toString()
        acneSaran = intent.getStringExtra(ACNE_SARAN).toString()

        acneImage = intent.getParcelableExtra<Bitmap>("USED_IMAGE")!!

        binding.ivAcne.setImageBitmap(acneImage)
        binding.tvAcneName.text = acneName
        binding.tvAcneDesc.text = acneDesc
        binding.tvAcneSol.text = acneSol
        binding.tvAcneSaran.text = acneSaran
    }
}