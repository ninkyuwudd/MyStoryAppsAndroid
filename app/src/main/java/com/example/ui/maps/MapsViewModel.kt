package com.example.ui.maps

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.model.ListStoryItem
import com.example.ourstoryapps.data.model.ResponseStory
import retrofit2.Call
import retrofit2.Response

class MapsViewModel:ViewModel() {
    private val _listLatLongSotry = MutableLiveData<List<ListStoryItem>>()
    val listOurStory : LiveData<List<ListStoryItem>> = _listLatLongSotry


    fun fetchDataLatLongSotry(token: String){
        val client = ApiConfig.apiServiceGet(token).getStoriesWithLocation()

        client.enqueue(object : retrofit2.Callback<ResponseStory>{
            override fun onResponse(call: Call<ResponseStory>, response: Response<ResponseStory>) {
                if(response.isSuccessful){
                    _listLatLongSotry.value = response.body()?.listStory
                }else{
                    Log.e(ContentValues.TAG,"onFilurecuy ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseStory>, t: Throwable){
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })

    }
}