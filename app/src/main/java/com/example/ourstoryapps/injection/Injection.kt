package com.example.ourstoryapps.injection

import android.content.Context
import com.example.ourstoryapps.data.AkunPreference
import com.example.ourstoryapps.data.AkunRepository
import com.example.ourstoryapps.data.dataStore

object Injection {
    fun provideRepo(context: Context) : AkunRepository {
        val pref = AkunPreference.getInstance(context.dataStore)
        return  AkunRepository.instanceGet(pref)
    }
}