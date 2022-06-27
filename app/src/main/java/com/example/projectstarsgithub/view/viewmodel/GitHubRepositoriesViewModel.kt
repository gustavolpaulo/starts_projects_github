package com.example.projectstarsgithub.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projectstarsgithub.costants.Constants
import com.example.projectstarsgithub.models.GitHubRepositoriesInfoModel
import com.example.projectstarsgithub.network.GitHubRepositoriesRepository
import com.example.projectstarsgithub.service.dataAccess.DataInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitHubRepositoriesViewModel(context: Context) : ViewModel() {
  private val repository = GitHubRepositoriesRepository(context)
  var dataInfo: DataInfo? = null
  private var currentPage = 0

  fun clearData(): Completable {
    currentPage = 0
    dataInfo = null
    return repository.clearData()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
  }

  fun getDataInfo(): Single<DataInfo?> {
    return repository.getDataInfo().map {
      dataInfo = it
      it
    }.observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
  }

  fun searchGitHubRepositories(
    page: Int
  ): Observable<List<GitHubRepositoriesInfoModel>> {

    if (dataInfo != null && page <= dataInfo?.lastPage!!) {
      currentPage = page

      return repository.getCacheRepositories()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    } else if (currentPage != page) {
      currentPage = page

      return repository.searchGitHubRepositories(
        Constants.LANGUAGE_KOTLIN,
        Constants.STARS,
        page
      ).map {
        it
      }.doOnError {
        Log.e(Constants.REPOSITORIES_VIEW_MODEL, it.message.toString())
      }.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
    } else {
      return Observable.just(ArrayList())
    }
  }
}