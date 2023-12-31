package com.example.ourstoryapps.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ourstoryapps.data.AkunRepository
import com.example.ui.homepage.HomepageViewModel
import com.example.ourstoryapps.injection.Injection
import com.example.ui.login.LoginViewModel
import com.example.ui.maps.MapsViewModel

class ViewModelFactory(private val repository: AkunRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomepageViewModel::class.java) -> {
                HomepageViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context) : ViewModelFactory {
            if(INSTANCE == null){
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepo(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }


    }

}