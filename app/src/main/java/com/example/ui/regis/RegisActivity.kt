package com.example.ui.regis

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
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
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiRepo = ApiRepository(ApiConfig.apiServiceGet(viewModelLogin.sessionGet().value?.token))
        viewModel = ViewModelProvider(this, AuthViewModelFactory(apiRepo))[AuthViewModel::class.java]

        editTextName = binding.edRegisterName
        editTextEmail = binding.edRegisterEmail
        editTextPass = binding.edRegisterPassword


        viewModel.liveDataResponse.observe(this) { response ->

            if (response.isSuccessful) {
//                val body = response.body()
//                Log.d("sscess","success response cuy")
                onBackPressed()
            } else {
//                val error = response.errorBody()
                showLoading(false)
//                binding.errorWarning.text = "User already exists or wrong data!"
//                binding.errorWarning.visibility =View.VISIBLE
            }
        }


        binding.buttonRegis.setOnClickListener {

            if(editTextName?.text!!.isNotEmpty() && editTextEmail?.text!!.isNotEmpty() && editTextPass?.text!!.isNotEmpty()){
                showLoading(true)
                viewModel.regis(editTextName?.text.toString(),editTextEmail?.text.toString(),editTextPass?.text.toString())
            }else{
                showLoading(false)
                binding.errorWarning.text = "Field can't be empty !"
                binding.errorWarning.visibility = View.VISIBLE
            }
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