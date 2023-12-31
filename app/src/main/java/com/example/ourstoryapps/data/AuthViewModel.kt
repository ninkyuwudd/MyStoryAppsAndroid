package com.example.ourstoryapps.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourstoryapps.data.api.ApiRepository
import com.example.ourstoryapps.data.model.ResponseLogin
import com.example.ourstoryapps.data.model.ResponseRegister
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private  val _liveDataResponse = MutableLiveData<Response<ResponseRegister>>()
    val liveDataResponse: LiveData<Response<ResponseRegister>> get()= _liveDataResponse


    private val _liveDataResponseLogin  = MutableLiveData<Response<ResponseLogin>>()
    val liveDataResponseLogin : LiveData<Response<ResponseLogin>> get() = _liveDataResponseLogin

    fun regis(name:String,email:String,password:String){
        viewModelScope.launch {
            try {
                val theResponse = apiRepository.register(name,email,password)
                _liveDataResponse.value = theResponse
            }catch (e:Exception){
                Log.e("error",e.toString())
            }
        }
    }


    fun login(email: String,password: String){
        viewModelScope.launch {
            try {
                val theResponse = apiRepository.login(email,password)
                _liveDataResponseLogin.value = theResponse
            }catch (e:Exception){
                Log.e("error",e.toString())
            }
        }
    }
}