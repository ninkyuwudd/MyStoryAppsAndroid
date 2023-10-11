package com.example.ourstoryapps.data.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.model.ResponseDetailStory
import com.example.ourstoryapps.data.model.ResponseStory
import com.example.ourstoryapps.data.model.Story
import retrofit2.Call
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailDataStory = MutableLiveData<Story>()
    val DetailDataAccount : LiveData<Story> =_detailDataStory


    fun findDetailUsernameAccount(token:String,id:String){

        val client = ApiConfig.apiServiceGet(token).getDataStories(id)
        client.enqueue(object : retrofit2.Callback<ResponseDetailStory>{
            override fun onResponse(
                call: Call<ResponseDetailStory>,
                response: Response<ResponseDetailStory>
            ){

                if(response.isSuccessful){
                    _detailDataStory.value = response.body()?.story
                }else{
                    Log.e(ContentValues.TAG,"onFilure ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseDetailStory>, t: Throwable) {

                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}