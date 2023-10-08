package com.example.ourstoryapps.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ourstoryapps.data.AuthViewModel
import com.example.ourstoryapps.data.api.ApiRepository
import com.example.ourstoryapps.injection.Injection

class AuthViewModelFactory(private val apiRepository: ApiRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(apiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}