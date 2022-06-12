package com.capstone.bangkit.skinkr.presentation.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.bangkit.skinkr.databinding.ActivityResultBinding
import com.capstone.bangkit.skinkr.presentation.home.HomeActivity

class ResultActivity : AppCompatActivity() {


    private lateinit var binding: ActivityResultBinding

    companion object {
        val ACNE_NAME = "ACNE_NAME"
        val ACNE_SOLUSI = "ACNE_SOLUSI"
        val ACNE_SARAN = "ACNE_SARAN"
        val ACNE_DESC = "ACNE_DESC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAcneName.text = intent.getStringExtra("ACNE_NAME")
        binding.tvAcneDesc.text = intent.getStringExtra("ACNE_DESC")
        binding.loremSaran.text = intent.getStringExtra("ACNE_SARAN")
        binding.loremSolusi.text = intent.getStringExtra("ACNE_SOLUSI")

        binding.btnDone.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

    }
}