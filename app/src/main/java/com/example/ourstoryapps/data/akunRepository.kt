package com.example.ourstoryapps.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.ourstoryapps.data.api.ApiService
import com.example.ourstoryapps.data.model.ListStoryItem
import com.example.ourstoryapps.data.paging.OurStoryPaggingSource
import kotlinx.coroutines.flow.Flow

class AkunRepository private constructor(
    private val akunPreference: AkunPreference,private  val apiService: ApiService
) {

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                OurStoryPaggingSource(apiService)
            }
        ).liveData
    }



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
                instance ?: AkunRepository(akunPreference,apiService)
            }.also { instance = it }
    }

}