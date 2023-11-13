package com.raj.takehome.datamodel

data class UserDataModel(
    var userDetails: UserDetailsResponse? = null,
    var repos: List<UserRepos>? = null
)
