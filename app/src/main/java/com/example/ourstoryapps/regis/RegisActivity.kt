package com.example.ourstoryapps.regis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ourstoryapps.R
import com.example.ourstoryapps.data.AuthViewModel
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.api.ApiRepository
import com.example.ourstoryapps.databinding.ActivityRegisBinding
import com.example.ourstoryapps.factory.AuthViewModelFactory
import com.example.ourstoryapps.factory.ViewModelFactory
import com.example.ourstoryapps.login.LoginViewModel

class RegisActivity : AppCompatActivity() {

    private val viewModelLogin by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }


    private lateinit var viewModel: AuthViewModel

    private lateinit var binding: ActivityRegisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiRepo = ApiRepository(ApiConfig.apiServiceGet(viewModelLogin.sessionGet().value?.token))
        viewModel = ViewModelProvider(this, AuthViewModelFactory(apiRepo))[AuthViewModel::class.java]


        viewModel.liveDataResponse.observe(this,{
            response ->

            if (response.isSuccessful){
                val body = response.body()
                Log.d("sscess","success response cuy")
            }else{
                val error = response.errorBody()
            }
        })


        binding.buttonRegis.setOnClickListener {
            viewModel.regis("wudd","wudd404@gmail.com","12345678")
        }

    }
}