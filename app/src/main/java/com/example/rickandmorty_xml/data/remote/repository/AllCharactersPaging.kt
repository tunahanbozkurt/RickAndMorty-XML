package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AllCharactersPaging(
    private val api: RickAndMortyAPI,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = withContext(ioDispatcher) {
                val request = api.getAllCharacters(currentPage)

                if (request.isSuccessful) {
                    return@withContext request.body()
                } else {
                    throw HttpException(request)
                }
            }

            LoadResult.Page(
                data = response!!.results,
                prevKey = null,
                nextKey = currentPage.plus(1),
            )

        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}