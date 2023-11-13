package com.raj.takehome.service

/*
* Generic <T> API Response Sates to update the UI View state
 */
sealed class ApiResponse<out T> {
    data class Success<out T>(val apiResponseData: T? = null) : ApiResponse<T>()
    data class Loading(val nothing: Nothing?=null) : ApiResponse<Nothing>()
    data class Error(val msg: String?) : ApiResponse<Nothing>()
}
