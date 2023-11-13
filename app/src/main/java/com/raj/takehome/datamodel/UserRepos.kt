package com.raj.takehome.datamodel

data class UserRepos(
    var name: String? = null,
    var description: String? = null,
    var updated_at: String? = null,
    var stargazers_count: Int? = null,
    var forks: Int? = null,
    var id: Long? = null
)