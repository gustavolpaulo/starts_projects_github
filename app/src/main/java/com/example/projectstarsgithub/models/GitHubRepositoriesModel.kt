package com.example.projectstarsgithub.models

import com.google.gson.annotations.SerializedName

class GitHubRepositoriesModel {

  @SerializedName("items")
  lateinit var items: List<GitHubRepositoriesInfoModel>

}