package com.example.projectstarsgithub.costants

object Constants {

  //Network
  const val HOST = "https://api.github.com/"
  const val SEARCH_ENDPOINT = "search/repositories"

  //Network - Query
  const val QUERY = "q"
  const val SORT = "sort"
  const val PAGE = "page"

  // Network - Query - Value
  const val LANGUAGE_KOTLIN = "language:kotlin"
  const val STARS = "stars"

  //views
  const val REPOSITORIES_VIEW_MODEL = "RepositoriesViewModel"
  const val REPOSITORIES_VIEW = "RepositoriesView"
  const val QUERY_LIMIT_EXCEEDED = "Você atingiu o limite para consultas não autenticada"

  const val LIST_REPOSITORIES = "LIST_REPOSITORIES"
}