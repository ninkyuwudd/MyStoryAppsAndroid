package com.example.ui.regis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
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
import com.example.ui.login.LoginViewModel

class RegisActivity : AppCompatActivity() {

    private val viewModelLogin by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }


    private lateinit var viewModel: AuthViewModel

    private lateinit var binding: ActivityRegisBinding

    private var editTextName : EditText? = null
    private var editTextEmail : EditText? = null
    private  var editTextPass : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiRepo = ApiRepository(ApiConfig.apiServiceGet(viewModelLogin.sessionGet().value?.token))
        viewModel = ViewModelProvider(this, AuthViewModelFactory(apiRepo))[AuthViewModel::class.java]

        editTextName = binding.edRegisName
        editTextEmail = binding.edLoginEmail
        editTextPass = binding.edRegisPassword


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

            if(editTextName?.text!!.isNotEmpty() && editTextEmail?.text!!.isNotEmpty() && editTextPass?.text!!.isNotEmpty()){
                viewModel.regis(editTextName?.text.toString(),editTextEmail?.text.toString(),editTextPass?.text.toString())

            }else{
                binding.errorWarning.visibility = View.VISIBLE
            }
        }

    }
}