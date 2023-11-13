package com.raj.takehome.service

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper{
    override suspend fun getUserDetails(userName: String) = apiService.getUserDetails(userName)
    override suspend fun getUserRepos(userName: String) = apiService.getUserRepos(userName)
}