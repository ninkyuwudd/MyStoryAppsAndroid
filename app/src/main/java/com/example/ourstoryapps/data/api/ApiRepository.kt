package com.example.ourstoryapps.data.api

import com.example.ourstoryapps.data.model.ResponseLogin
import com.example.ourstoryapps.data.model.ResponseRegister
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {
    suspend fun register(name:String,email:String,password:String): Response<ResponseRegister> {
        return apiService.register(name,email,password)
    }

    suspend fun login(email: String,password: String): Response<ResponseLogin> {
        return  apiService.login(email,password)
    }
}