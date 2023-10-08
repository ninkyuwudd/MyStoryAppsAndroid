package com.example.ourstoryapps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourstoryapps.data.api.ApiRepository
import com.example.ourstoryapps.data.model.ResponseRegister
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private  val _liveDataResponse = MutableLiveData<Response<ResponseRegister>>()
    val liveDataResponse: LiveData<Response<ResponseRegister>> get()= _liveDataResponse

    fun regis(name:String,email:String,password:String){
        viewModelScope.launch {
            try {
                val theResponse = apiRepository.register(name,email,password)
                _liveDataResponse.value = theResponse
            }catch (e:Exception){

            }
        }
    }
}