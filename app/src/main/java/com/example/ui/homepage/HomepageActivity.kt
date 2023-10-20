package com.example.ui.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourstoryapps.MainActivity
import com.example.ourstoryapps.R
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.AuthViewModel
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.api.ApiRepository
import com.example.ourstoryapps.data.model.ListStoryItem
import com.example.ourstoryapps.data.model.ResponseLogin
import com.example.ourstoryapps.databinding.ActivityAddStoryBinding
import com.example.ourstoryapps.factory.ViewModelFactory
import com.example.ourstoryapps.databinding.ActivityHomepageBinding
import com.example.ourstoryapps.factory.AuthViewModelFactory
import com.example.ui.login.LoginViewModel
import com.example.ui.maps.MapsActivity
import com.example.ui.story.AddStoryActivity
import retrofit2.Response


class HomepageActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomepageViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val viewModelToken by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var viewModelApi: AuthViewModel

    private lateinit var thetoken : String


    private lateinit var storyAdapter : HomePageAdapter

    private lateinit var binding:ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val layoutManager = LinearLayoutManager(this)
        binding.rvListStory.layoutManager = layoutManager

        val itemDecorator = DividerItemDecoration(this,layoutManager.orientation)
        binding.rvListStory.addItemDecoration(itemDecorator)
        storyAdapter = HomePageAdapter()
        binding.rvListStory.adapter = storyAdapter

        binding.addBtn.setOnClickListener{
            val itn = Intent(this@HomepageActivity,AddStoryActivity::class.java)
            startActivity(itn)
        }



        viewModelToken.sessionGet().observe(this){
                islogin:AkunModel ->
            if(islogin.token != ""){
                viewModel.fetchDataStory(islogin.token)
            }

        }




        viewModel.sessionGet().observe(this) { user ->
            if (!user.loginState) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
//
//


        viewModel.listOurStory.observe(this){
                username -> setStoryData(username)
        }

        val apiRepo = ApiRepository(ApiConfig.apiServiceGet(viewModel.sessionGet().value?.token))
        viewModelApi = ViewModelProvider(this, AuthViewModelFactory(apiRepo))[AuthViewModel::class.java]
        logoutBtnFunction()
    }

    private fun setStoryData(usernameData:List<ListStoryItem>){
        val adapter = HomePageAdapter()
        adapter.submitList(usernameData)
        binding.rvListStory.adapter = adapter
    }



    private fun logoutBtnFunction(){

        binding.appbarid.setOnMenuItemClickListener{
            menuitem ->
            when(menuitem.itemId){
                R.id.lgIcon -> {
                    viewModel.logOut()
                    true
                }
                R.id.mapsIcon -> {
                    val itn = Intent(this,MapsActivity::class.java)
                    startActivity(itn)
                    true
                }
                else -> false
            }
        }

//        binding.testbtn.setOnClickListener {
//            Log.d("getToken",
//                thetoken
//            )
//        }
//
//        binding.logoutbtn.setOnClickListener {
//            Log.d("testing","clicked lgout")
//            viewModel.logOut()
//        }
    }
}