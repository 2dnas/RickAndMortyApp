package com.example.rickandmorty.paging

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.api.ApiService
import com.example.rickandmorty.api.ApiUtils
import com.example.rickandmorty.db.App
import com.example.rickandmorty.model.Episode
import com.example.rickandmorty.model.EpisodesModel
import java.lang.Exception

class EpisodesDataSource(private val api : ApiService) : PagingSource<Int,Episode>() {
    var loadedItems = 0
    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response : List<Episode>
            val episodes = App.instance.db.getEpisodeDao().getEpisodes()
            if(episodes!!.isNotEmpty() && episodes.size >= loadedItems){
                response = episodes.subList(loadedItems,episodes.size)
                loadedItems += 20
            } else {
                response = api.getEpisodes(nextPageNumber).results
                response.forEach { App.instance.db.getEpisodeDao().insert(it) }
                loadedItems += 20
            }


            LoadResult.Page(
                data = response,
                prevKey = if(nextPageNumber > 0 ) nextPageNumber - 1 else null,
                nextKey = if(nextPageNumber< 3) nextPageNumber + 1 else null
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}