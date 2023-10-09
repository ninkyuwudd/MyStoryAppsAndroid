package com.example.ourstoryapps.data

import com.example.ourstoryapps.data.api.ApiService
import kotlinx.coroutines.flow.Flow

class AkunRepository private constructor(
    private val akunPreference: AkunPreference
) {

    suspend fun sessionSave(akun : AkunModel){
        akunPreference.sessionSave(akun)
    }

    fun sessionGet() : Flow<AkunModel>{
        return akunPreference.sessionGet()
    }

    suspend fun logout(){
        akunPreference.logout()
    }

    companion object{
        @Volatile
        private var instance : AkunRepository? = null
        fun instanceGet(
            apiService: ApiService,
            akunPreference: AkunPreference
        ): AkunRepository =
            instance ?: synchronized(this){
                instance ?: AkunRepository(akunPreference)
            }.also { instance = it }
    }

}