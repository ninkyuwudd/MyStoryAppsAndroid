package com.example.ourstoryapps.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.ourstoryapps.MainActivity
import com.example.ourstoryapps.R
import com.example.ourstoryapps.ViewModelFactory
import com.example.ourstoryapps.databinding.ActivityHomepageBinding
import com.example.ourstoryapps.databinding.ActivityMainBinding

class HomepageActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomepageViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding:ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.sessionGet().observe(this) { user ->
            if (!user.loginState) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        logoutBtnFunction()
    }


    private fun logoutBtnFunction(){
        binding.logoutbtn.setOnClickListener {
            Log.d("testing","clicked lgout")
            viewModel.logOut()
        }
    }
}