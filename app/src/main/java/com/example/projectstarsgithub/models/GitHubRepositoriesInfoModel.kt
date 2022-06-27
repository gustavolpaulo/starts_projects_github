package com.example.projectstarsgithub.models

import com.google.gson.annotations.SerializedName

class GitHubRepositoriesInfoModel {

  @SerializedName("name")
  lateinit var name: String

  @SerializedName("stargazers_count")
  var stargazersCount: Int = 0

  @SerializedName("forks_count")
  var forksCount: Int = 0

  @SerializedName("owner")
  lateinit var owner: OwnerModel
}