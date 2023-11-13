package com.raj.takehome.domain.repository

import com.raj.takehome.service.ApiHelper
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getUserDetails(userName: String) = apiHelper.getUserDetails(userName)
    suspend fun getUserRepos(userName: String) = apiHelper.getUserRepos(userName)

}