package com.example.ourstoryapps.data.api

import com.example.ourstoryapps.data.model.ResponseRegister
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {
    suspend fun register(name:String,email:String,password:String): Response<ResponseRegister> {
        return apiService.register(name,email,password)
    }
}