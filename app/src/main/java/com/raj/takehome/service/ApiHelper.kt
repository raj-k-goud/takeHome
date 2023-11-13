package com.raj.takehome.service

import com.raj.takehome.datamodel.ReposResponse
import com.raj.takehome.datamodel.UserDetailsResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getUserDetails(userName: String): Response<UserDetailsResponse>
    suspend fun getUserRepos(userName: String): Response<ReposResponse>
}