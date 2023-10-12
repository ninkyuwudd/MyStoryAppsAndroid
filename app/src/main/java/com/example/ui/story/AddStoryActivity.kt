package com.example.ui.story

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ourstoryapps.R
import com.example.ourstoryapps.databinding.ActivityAddStoryBinding
import com.example.utils.getImageUri

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding

    private var imgUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.btnOpenGallery.setOnClickListener { galleryStart() }

        binding.btnOpenCamera.setOnClickListener {cameraStart()}


    }


    private fun cameraStart() {
        imgUri = getImageUri(this)
        launcherIntentCamera.launch(imgUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            displayImage()
        }
    }

    private fun galleryStart() {
        galleryLaunch.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val galleryLaunch = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imgUri = uri
            displayImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }


    private fun displayImage() {
        imgUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imgPreview.setImageURI(it)
        }
    }
}