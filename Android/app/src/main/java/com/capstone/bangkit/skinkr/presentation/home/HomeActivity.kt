package com.capstone.bangkit.skinkr.presentation.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.bangkit.skinkr.data.repository.ResultRespond
import com.capstone.bangkit.skinkr.databinding.ActivityHomeBinding
import com.capstone.bangkit.skinkr.presentation.*
import com.capstone.bangkit.skinkr.presentation.result.ResultActivity
import org.json.JSONObject
import java.io.File


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel : HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var layoutManager: LinearLayoutManager

    private var base64Image : String? = null

    private lateinit var currentPhotoPath : String
    private var getFile : File? = null

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnInsertPhoto.setOnClickListener { startTakePhoto() }
        binding.uploadAndScan.setOnClickListener { uploadToCC() }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.capstone.bangkit.skinkr",
                it
            )
            currentPhotoPath = it.absolutePath

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile


           val result = rotateBitmap(BitmapFactory.decodeFile(getFile?.path))
           binding.imageFace.setImageBitmap(result)
        }
    }

    private fun uploadToCC() {
        if(getFile != null) {
            val file = reduceFileImage(getFile as File)
            homeViewModel.uploadAndScan(file).observe(this){ result->
                if(result != null) {
                    when (result) {
                        is ResultRespond.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                        is ResultRespond.Success -> {
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(ResultActivity.ACNE_NAME,"ACNE_NAME")
                            startActivity(intent)
                            binding.loading.visibility = View.GONE
                        }
                        is ResultRespond.Error -> {
                            binding.loading.visibility = View.GONE
                            Toast.makeText(this,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this,"harap masukan file",Toast.LENGTH_LONG).show()
        }
    }


}