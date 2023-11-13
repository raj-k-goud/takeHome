package com.raj.takehome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raj.takehome.utils.Constants.API_CODE_SUCCESS
import com.raj.takehome.service.ApiResponse
import com.raj.takehome.datamodel.UserDetailsResponse
import com.raj.takehome.datamodel.UserRepos
import com.raj.takehome.domain.repository.ApiRepository
import com.raj.takehome.utils.Constants.TOTAL_FORKS_5000
import com.raj.takehome.utils.Utility.exceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: ApiRepository,
) : ViewModel() {

    private val _userDetails = MutableLiveData<ApiResponse<UserDetailsResponse>>()
    val userDetails: LiveData<ApiResponse<UserDetailsResponse>> = _userDetails

    private val _repoDetails = MutableLiveData<ApiResponse<List<UserRepos>>>()
    val repoDetails: LiveData<ApiResponse<List<UserRepos>>> = _repoDetails

    fun fetchData(userName: String) = viewModelScope.launch(exceptionHandler) {
        val userResponse = async{repository.getUserDetails(userName)}.await()
        val gitResponse = async{repository.getUserRepos(userName)}.await()

        if (userResponse.isSuccessful && userResponse.code() == API_CODE_SUCCESS) {
            _userDetails.postValue(ApiResponse.Success(userResponse.body()))
        } else {
            _userDetails.postValue(ApiResponse.Error(userResponse.code().toString()))
        }

        if (gitResponse.isSuccessful && gitResponse.code() == API_CODE_SUCCESS) {
            _repoDetails.postValue(ApiResponse.Success(gitResponse.body()))
        } else {
            _repoDetails.postValue(ApiResponse.Error(gitResponse.code().toString()))
        }
    }

    fun getGitDetailsbyId(id: Long): UserRepos? {
        (_repoDetails.value as ApiResponse.Success).apiResponseData?.forEach {
            if (id == it.id) {
                return it
            }
        }
        return null
    }
    fun isTotalForks(): Boolean {
        var totalForks = 0
        (_repoDetails.value as ApiResponse.Success).apiResponseData?.forEach {
            it.forks?.let {
                totalForks += it
            }
        }
        return (totalForks> TOTAL_FORKS_5000)
    }
}