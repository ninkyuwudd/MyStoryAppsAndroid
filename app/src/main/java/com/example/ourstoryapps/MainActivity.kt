package com.example.ourstoryapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ourstoryapps.databinding.ActivityMainBinding
import com.example.ui.login.LoginActivity
import com.example.ui.regis.RegisActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonContinue.setOnClickListener {
            val itn = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(itn)
        }

        binding.buttonRegister.setOnClickListener {
            val itn = Intent(this@MainActivity, RegisActivity::class.java)
            startActivity(itn)
        }
    }
}