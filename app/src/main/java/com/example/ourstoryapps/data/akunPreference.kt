package com.example.ourstoryapps.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class AkunPreference private constructor(private val akunDataStore: DataStore<Preferences>){

    companion object {
        @Volatile
        private var INSTANCE: AkunPreference? = null
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_TOKEN =  stringPreferencesKey("token")
        private val KEY_LOGIN_STATE = booleanPreferencesKey("stateLogin")

        fun getInstance(akunDataStore: DataStore<Preferences>): AkunPreference {
            return INSTANCE ?: synchronized(this){
                val instance = AkunPreference(akunDataStore)
                INSTANCE = instance
                instance
            }
        }
    }


    suspend fun sessionSave(akun : AkunModel){
        akunDataStore.edit {
            preference ->
            preference[KEY_EMAIL] = akun.email
            preference[KEY_TOKEN] = akun.token
            preference[KEY_LOGIN_STATE] = true
        }
    }

    fun sessionGet(): Flow<AkunModel>{
            return akunDataStore.data.map {
                preference ->
                AkunModel(
                    preference[KEY_EMAIL] ?: "",
                    preference[KEY_TOKEN] ?: "",
                    preference[KEY_LOGIN_STATE] ?: false
                )
            }
    }


    suspend fun logout() {
        akunDataStore.edit { preferences ->
            preferences.clear()
        }
    }


}