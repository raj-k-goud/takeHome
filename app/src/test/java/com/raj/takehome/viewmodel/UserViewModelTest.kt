package com.raj.takehome.viewmodel

import androidx.lifecycle.MutableLiveData
import com.raj.takehome.service.ApiHelper
import com.raj.takehome.service.ApiResponse
import com.raj.takehome.datamodel.UserDetailsResponse
import com.raj.takehome.datamodel.UserRepos
import com.raj.takehome.domain.repository.ApiRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class UserViewModelTest {

    private lateinit var viewModel: UserViewModel
    private val apiHelper: ApiHelper = mockk()
    private val repo: ApiRepository = ApiRepository(apiHelper)
    private lateinit var userDetails: MutableLiveData<ApiResponse<UserDetailsResponse>>
    private lateinit var repoDetails: MutableLiveData<ApiResponse<UserRepos>>
    private var userDetailsResponse: ApiResponse<UserDetailsResponse> =
        ApiResponse.Success(apiResponseData = UserDetailsResponse("name", "url"))
    private var repoDetailsResponse: ApiResponse<UserRepos> =
        ApiResponse.Success(apiResponseData = UserRepos("name", "desc", "date", 1, 1, 1))
    private val userName = "username"

    @get:Rule
    val rule: TestRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = UserViewModel(repo)
        viewModel.userDetails.observeForever {
            userDetails.postValue(userDetailsResponse)
        }
        viewModel.repoDetails.observeForever {
            repoDetails.postValue(repoDetailsResponse)
        }

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getGitUserDetailsApiResponseTest() {
        val test = viewModel.fetchData(userName)
        coEvery {
            repo.getUserRepos(any())
        }
        assertEquals(test.isCompleted, true)
        assertNotNull(viewModel.userDetails)
        assertNotNull(userDetailsResponse)
    }

    @Test
    fun getGitRepoDetailsApiResponseTest() {
        val test = viewModel.fetchData(userName)
        coEvery {
            repo.getUserRepos(any())
        }
        assertEquals(test.isCompleted, true)
        assertNotNull(viewModel.repoDetails)
        assertNotNull(repoDetailsResponse)

    }

    @Test
    fun isTotalForks() {
        val repos = UserRepos("name", "desc", "date", 1, 6000, 1)
        var isTotalForks = false
        if (repos.forks!! > 5000) {
            isTotalForks = true
        }
        assertEquals(isTotalForks, true)
    }
}