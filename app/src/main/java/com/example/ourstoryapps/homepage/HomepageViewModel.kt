package com.example.ourstoryapps.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.AkunRepository
import kotlinx.coroutines.launch

class HomepageViewModel(private val repository: AkunRepository): ViewModel() {
    fun sessionGet(): LiveData<AkunModel>{
        return  repository.sessionGet().asLiveData()
    }

    fun logOut(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}