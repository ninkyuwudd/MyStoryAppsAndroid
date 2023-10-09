package com.example.ourstoryapps.injection

import android.content.Context
import com.example.ourstoryapps.data.AkunPreference
import com.example.ourstoryapps.data.AkunRepository
import com.example.ourstoryapps.data.api.ApiConfig
import com.example.ourstoryapps.data.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepo(context: Context) : AkunRepository {
        val pref = AkunPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.sessionGet().first()}
        val apiService = ApiConfig.apiServiceGet(user.token)
        return  AkunRepository.instanceGet(apiService,pref)
    }
}