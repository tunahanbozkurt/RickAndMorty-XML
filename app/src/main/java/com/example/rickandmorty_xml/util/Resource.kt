package com.example.rickandmorty_xml.util

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}

fun <T, K> Resource<T>.convert(block: (T) -> K): Resource<K> {
    return when (this) {
        is Resource.Loading -> {
            Resource.Loading()
        }
        is Resource.Success -> {
            Resource.Success(block.invoke(this.data))
        }
        is Resource.Error -> {
            Resource.Error(this.message)
        }
    }
}
