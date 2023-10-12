package com.example.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourstoryapps.data.AkunModel
import com.example.ourstoryapps.data.AkunRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AkunRepository): ViewModel() {
    fun sessionSave(akun: AkunModel){
        viewModelScope.launch {
            repository.sessionSave(akun)
        }
    }


    fun sessionGet():LiveData<AkunModel>{
        return repository.sessionGet().asLiveData()
    }

}