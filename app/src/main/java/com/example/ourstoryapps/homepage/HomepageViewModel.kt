package com.example.ourstoryapps.homepage

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.AkunRepository
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.model.ListStoryItem
import com.example.ourstoryapps.data.model.ResponseStory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Response

class HomepageViewModel(private val repository: AkunRepository): ViewModel() {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listOurStory : LiveData<List<ListStoryItem>> = _listStory


    fun fetchDataStory(token: String){
        val client = ApiConfig.apiServiceGet(token).getStories()

        client.enqueue(object : retrofit2.Callback<ResponseStory>{
            override fun onResponse(call: Call<ResponseStory>, response: Response<ResponseStory>) {
                if(response.isSuccessful){
                    _listStory.value = response.body()?.listStory
                }else{
                    Log.e(ContentValues.TAG,"onFilurecuy ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseStory>, t: Throwable){
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })

    }



    fun sessionGet(): LiveData<AkunModel>{
        return  repository.sessionGet().asLiveData()
    }

    fun logOut(){
        viewModelScope.launch {
            repository.logout()
        }
    }

    companion object {
        fun sessionGet(homepageViewModel: HomepageViewModel):LiveData<AkunModel>{
            return homepageViewModel.repository.sessionGet().asLiveData()
        }
    }
}