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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
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



    private lateinit var viewModelApi: AuthViewModel

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewSetup()
        actionSetup()

        setTheAnimation()



        viewModel.sessionGet().observe(this){
            islogin:AkunModel ->
            if(islogin.loginState && islogin.token != ""){
                Log.d("sessionGet",islogin.token)
                val itn = Intent(this@LoginActivity, HomepageActivity::class.java)
                startActivity(itn)
            }
        }



        val apiRepo = ApiRepository(ApiConfig.apiServiceGet(viewModel.sessionGet().value?.token))
        viewModelApi = ViewModelProvider(this, AuthViewModelFactory(apiRepo))[AuthViewModel::class.java]

        viewModelApi.liveDataResponseLogin.observe(this){
            getToken:Response<ResponseLogin> ->
            if(getToken.body()?.loginResult?.token != null){
                val email = "wudd404@gmail.com"
                val token = getToken.body()!!.loginResult!!.token
                Log.d("condition",token.toString())
                viewModel.sessionSave(AkunModel(email,token.toString()))
                val itn = Intent(this@LoginActivity, HomepageActivity::class.java)
                startActivity(itn)
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
            val email = binding.edLoginEmail.text.toString()

            viewModelApi.login("wudd404@gmail.com","12345678")

            if (viewModelApi.liveDataResponseLogin.value?.body()?.loginResult?.token != null){
                viewModel.sessionSave(AkunModel(email,viewModelApi.liveDataResponseLogin.value!!.body()!!.loginResult!!.token!!))
            }else{
                Log.d("condition","TOken kosong bro")
                viewModel.sessionSave(AkunModel(email,""))
            }
            AlertDialog.Builder(this).apply {
                setTitle("Nice")
                setMessage("Login Successfully!")
                setPositiveButton("Next"){
                    _,_ ->
//                    val itn = Intent(this@LoginActivity,HomepageActivity::class.java)
//                    itn.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(itn)
                    finish()
                }
                create()
                show()
            }
        }
    }
}