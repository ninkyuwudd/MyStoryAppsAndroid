package com.example.ourstoryapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.databinding.ActivityMainBinding
import com.example.ourstoryapps.factory.ViewModelFactory
import com.example.ui.homepage.HomepageActivity
import com.example.ui.login.LoginActivity
import com.example.ui.login.LoginViewModel
import com.example.ui.regis.RegisActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.sessionGet().observe(this){
                islogin: AkunModel ->
            if(islogin.loginState && islogin.token != ""){
                Log.d("sessionGet",islogin.token)
                val itn = Intent(this@MainActivity, HomepageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(itn)

            }
        }

        binding.buttonContinue.setOnClickListener {
            val itn = Intent(this@MainActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(itn)
        }

        binding.buttonRegister.setOnClickListener {
            val itn = Intent(this@MainActivity, RegisActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(itn)
        }
    }
}