package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PagingSource(
    private val api: RickAndMortyAPI
): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1

            val response = withContext(Dispatchers.IO) {
                return@withContext api.getAllCharacters(page = page)
            }

            return LoadResult.Page(
                data = response.body()?.results ?: throw Exception("Null results"),
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page < 42) page + 1 else null
            )
        }
        catch (e: IOException) {
            LoadResult.Error(e)
        }
        catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}




