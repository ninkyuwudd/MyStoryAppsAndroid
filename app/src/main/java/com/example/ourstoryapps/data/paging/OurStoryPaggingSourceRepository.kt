package com.example.ourstoryapps.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.ourstoryapps.data.api.ApiService
import com.example.ourstoryapps.data.model.ListStoryItem


class OurStoryPaggingSourceRepository(private val apiService: ApiService) {
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
}
