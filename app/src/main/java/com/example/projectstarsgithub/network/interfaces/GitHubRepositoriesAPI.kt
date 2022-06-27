package com.example.projectstarsgithub.network.interfaces

import com.example.projectstarsgithub.costants.Constants
import com.example.projectstarsgithub.models.GitHubRepositoriesModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubRepositoriesAPI {

  @GET(Constants.SEARCH_ENDPOINT)
  fun searchRepositories(
    @Query(Constants.QUERY) query: String,
    @Query(Constants.SORT) sort: String,
    @Query(Constants.PAGE) page: Int,
  ): Observable<GitHubRepositoriesModel>
}