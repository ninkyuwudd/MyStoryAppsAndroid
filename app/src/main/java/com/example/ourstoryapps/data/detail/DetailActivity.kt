package com.example.ourstoryapps.data.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ourstoryapps.R
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.model.Story
import com.example.ourstoryapps.databinding.ActivityDetailBinding
import com.example.ourstoryapps.factory.ViewModelFactory
import com.example.ui.login.LoginViewModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    companion object {
        const val EXTRA_ID = "extra_id"

    }

    private lateinit var detailViewModel: DetailViewModel

    private val viewModelToken by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        val id = intent.getStringExtra(EXTRA_ID)


        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModelToken.sessionGet().observe(this){
            data: AkunModel ->
            Log.d("testDetailToken",data.token)
            detailViewModel.findDetailUsernameAccount(data.token,id.toString())
        }


        detailViewModel.DetailDataAccount.observe(this){
            data -> setDetailItem(data)
        }

    }

    private fun setDetailItem(dataItem:Story){
        Log.d("data",dataItem.name.toString())
        binding.titleDetail.text = dataItem.name
        binding.detaiText.text = dataItem.description
        Picasso.get().load(dataItem.photoUrl).into(binding.imageDetail)
    }
}