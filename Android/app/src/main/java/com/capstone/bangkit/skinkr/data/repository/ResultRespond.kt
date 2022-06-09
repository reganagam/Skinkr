package com.capstone.bangkit.skinkr.data.repository

sealed class ResultRespond<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultRespond<T>()
    data class Error(val error: String) : ResultRespond<Nothing>()
    object Loading : ResultRespond<Nothing>()
}
