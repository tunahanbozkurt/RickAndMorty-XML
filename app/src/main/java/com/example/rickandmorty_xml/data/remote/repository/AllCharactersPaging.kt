package com.example.rickandmorty_xml.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty_xml.data.remote.RickAndMortyAPI
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.AllCharactersResult
import retrofit2.HttpException
import java.io.IOException

class AllCharactersPaging(
    private val api: RickAndMortyAPI
) : PagingSource<Int, AllCharactersResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllCharactersResult> {
        return try {
            val currentPage = params.key ?: 1

            val response = api.getAllCharacters(currentPage)
            val data = if (response.isSuccessful) response.body()!!.results
            else throw HttpException(response)

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if (currentPage != 42) currentPage.plus(1) else null,
            )

        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AllCharactersResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}