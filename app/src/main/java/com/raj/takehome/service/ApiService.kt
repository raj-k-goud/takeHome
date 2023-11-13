package com.raj.takehome.service

import com.raj.takehome.datamodel.ReposResponse
import com.raj.takehome.datamodel.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //Get API call to fetch User name and picture url
    @GET("/users/{USER_NAME}")
    suspend fun getUserDetails(@Path("USER_NAME") userName: String): Response<UserDetailsResponse>

    //Get API call to fetch list of Git Repose and details.
    @GET("/users/{USER_NAME}/repos")
    suspend fun getUserRepos(@Path("USER_NAME") userName: String): Response<ReposResponse>
}
