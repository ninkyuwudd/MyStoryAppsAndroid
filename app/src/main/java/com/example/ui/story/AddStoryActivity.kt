package com.example.ui.story

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.ourstoryapps.R
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.api.ApiRepository
import com.example.ourstoryapps.data.api.ApiService
import com.example.ourstoryapps.data.model.ResponseStoryUp
import com.example.ourstoryapps.databinding.ActivityAddStoryBinding
import com.example.ourstoryapps.factory.ViewModelFactory
import com.example.ui.homepage.HomepageActivity
import com.example.ui.login.LoginViewModel
import com.example.utils.getImageUri
import com.example.utils.reduceFileImage
import com.example.utils.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.awaitResponse

class AddStoryActivity : AppCompatActivity() {

    private val viewModelLogin by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityAddStoryBinding

//    private var currentImageUri: Uri? = null

    private var imgUri: Uri? = null

    private var edittext :EditText? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var apiRepo = ApiConfig.apiServiceGet(viewModelLogin.sessionGet().value?.token)
        viewModelLogin.sessionGet().observe(this){
            data:AkunModel ->
            apiRepo = ApiConfig.apiServiceGet(data.token)

        }


        edittext = binding.deskField

        binding.btnOpenGallery.setOnClickListener { galleryStart() }

        binding.btnOpenCamera.setOnClickListener {cameraStart()}

        binding.btnUpload.setOnClickListener {

            uploadImage(token = apiRepo, desk = edittext?.text.toString())
            Log.d("text_dari_textfield",edittext?.text.toString())
        }

        binding.backBtn.setOnClickListener { onBackPressed() }


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



    private fun uploadImage(token:ApiService,desk:String) {
        imgUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = desk

            showLoading(true)
            showLoadingCircle(true)

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    val successResponse = token.uploadImage(multipartBody,requestBody).awaitResponse().message()
                    showToast(successResponse)
                    showLoading(false)
                    showLoadingCircle(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ResponseStoryUp::class.java)
                    Log.d("cek error","ada error")
                    showToast(errorResponse.message.toString())

                    showLoading(false)
                    showLoadingCircle(false)
                }


            }
            binding.errorEmptyStory.visibility = View.GONE

//            val intent = Intent(this, HomepageActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//
//            startActivity(intent)
//
//            finish()
//
//            onResume(token)

        } ?: makeVisible()

//        val intent = Intent(this, HomepageActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//
//        startActivity(intent)
//
//        finish()
//
//        onResume(token)
    }

    private fun makeVisible(){
        binding.errorEmptyStory.visibility = View.VISIBLE
    }

    private fun onResume(apiServ: ApiService) {
        super.onResume()
        apiServ.getStories()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }


    private fun showLoadingCircle(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}

