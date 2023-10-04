package com.example.ourstoryapps.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.ourstoryapps.R
import com.example.ourstoryapps.ViewModelFactory
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.databinding.ActivityLoginBinding
import com.example.ourstoryapps.homepage.HomepageActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewSetup()
        actionSetup()


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
            viewModel.sessionSave(AkunModel(email,"the_token"))
            AlertDialog.Builder(this).apply {
                setTitle("Nice")
                setMessage("Login Successfully!")
                setPositiveButton("Next"){
                    _,_ ->
                    val itn = Intent(this@LoginActivity,HomepageActivity::class.java)
                    itn.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(itn)
                    finish()
                }
                create()
                show()
            }
        }
    }
}