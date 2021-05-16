package com.praveen.myapplication

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.praveen.myapplication.data.ItemsItem
import com.praveen.myapplication.service.ApiService

class PagingDataSource(private val apiService: ApiService) : PagingSource<Int, ItemsItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemsItem> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = apiService.getRepoData(nextPageNumber)
            LoadResult.Page(
                data = response?.items!!,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.totalCount ?: 0) nextPageNumber + 1 else null
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemsItem>): Int? {
        TODO("Not yet implemented")
    }
}