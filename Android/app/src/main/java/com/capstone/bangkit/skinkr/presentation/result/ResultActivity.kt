package com.capstone.bangkit.skinkr.presentation.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.capstone.bangkit.skinkr.R

class ResultActivity : AppCompatActivity() {



    companion object {
        val ACNE_NAME = "ACNE_NAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }
}