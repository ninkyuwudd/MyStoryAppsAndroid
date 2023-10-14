package com.example.ui.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.ourstoryapps.customview.customPassCheck
import com.example.ourstoryapps.factory.ViewModelFactory
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.AuthViewModel
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.api.ApiRepository
import com.example.ourstoryapps.data.model.ResponseLogin
import com.example.ourstoryapps.databinding.ActivityLoginBinding
import com.example.ourstoryapps.factory.AuthViewModelFactory
import com.example.ui.homepage.HomepageActivity
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var editTextEmail :EditText
    private lateinit  var editTextPass:EditText


    private lateinit var viewModelApi: AuthViewModel

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        editTextEmail = binding.edLoginEmail
        editTextPass = binding.edLoginPassword

        viewSetup()
        actionSetup()

        setTheAnimation()



        viewModel.sessionGet().observe(this){
            islogin:AkunModel ->
            if(islogin.loginState && islogin.token != ""){
                Log.d("sessionGet",islogin.token)
                val itn = Intent(this@LoginActivity, HomepageActivity::class.java)
                startActivity(itn)
                showLoading(false)
            }
        }



        val apiRepo = ApiRepository(ApiConfig.apiServiceGet(viewModel.sessionGet().value?.token))
        viewModelApi = ViewModelProvider(this, AuthViewModelFactory(apiRepo))[AuthViewModel::class.java]

        viewModelApi.liveDataResponseLogin.observe(this){
            getToken:Response<ResponseLogin> ->
            if(getToken.body()?.loginResult?.token != null){
                val email = editTextEmail.text.toString()
                val token = getToken.body()!!.loginResult!!.token
                Log.d("condition",token.toString())
                viewModel.sessionSave(AkunModel(email,token.toString()))
                val itn = Intent(this@LoginActivity, HomepageActivity::class.java)
                startActivity(itn)
            }else{
                showLoading(false)
            }
        }

        viewModelApi.liveDataResponse.observe(this,{
                response ->

            if (response.isSuccessful){
                val body = response.body()
                Log.d("sscess","success response cuy")

            }else{
                val error = response.errorBody()
            }
        })




    }


    private fun setTheAnimation(){
        ObjectAnimator.ofFloat(binding.titleLoginImage, View.TRANSLATION_X, -60f, 60f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()



    }


    private fun viewSetup(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun actionSetup(){



        binding.lgnButton.setOnClickListener {

            val email = editTextPass.text.toString()
            val pass = editTextPass.text.toString()


            if(email.isNotEmpty() && pass.isNotEmpty()){
            viewModelApi.login(editTextEmail.text.toString(),editTextPass.text.toString())

            if (viewModelApi.liveDataResponseLogin.value?.body()?.loginResult?.token != null){
                viewModel.sessionSave(AkunModel(email,viewModelApi.liveDataResponseLogin.value!!.body()!!.loginResult!!.token!!))
            }else{
                Log.d("condition","TOken kosong bro")
                viewModel.sessionSave(AkunModel(email,""))
            }
            showLoading(true)
                binding.textView6.visibility = View.GONE

            }else{
                binding.textView6.visibility = View.VISIBLE
            }
//            AlertDialog.Builder(this).apply {
//                setTitle("Nice")
//                setMessage("Login Successfully!")
//                setPositiveButton("Next"){
//                    _,_ ->
////                    val itn = Intent(this@LoginActivity,HomepageActivity::class.java)
////                    itn.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
////                    startActivity(itn)
//                    finish()
//                }
//                create()
//                show()
//            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}