package com.example.projectstarsgithub.network

import android.content.Context
import com.example.projectstarsgithub.costants.Constants
import com.example.projectstarsgithub.models.GitHubRepositoriesInfoModel
import com.example.projectstarsgithub.models.OwnerModel
import com.example.projectstarsgithub.network.interfaces.GitHubRepositoriesAPI
import com.example.projectstarsgithub.service.DbBitmapUtility
import com.example.projectstarsgithub.service.RepositoriesDatabase
import com.example.projectstarsgithub.service.dataAccess.DataInfo
import com.example.projectstarsgithub.service.dataAccess.Repositories
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*

class GitHubRepositoriesRepository(context: Context) {
  private var context = context

  fun clearData(): Completable {
    return Completable.fromAction { RepositoriesDatabase.getInstance(context).clearAllTables() }
  }

  fun getDataInfo(): Single<DataInfo?> {
    val db = RepositoriesDatabase.getInstance(context)
    val repositoriesDao = db.repositoriesDao()

    return repositoriesDao.getDataInfo().map {
      it
    }
  }

  private fun insertDataInfo(lastPage: Int) {
    val db = RepositoriesDatabase.getInstance(context)
    val repositoriesDao = db.repositoriesDao()

    repositoriesDao.clearTbDataInfo()

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
    val currentDate = sdf.format(Date())

    val dataInfo = DataInfo(lastPage = lastPage, initData = currentDate, id = 0)

    repositoriesDao.insertDatainfo(dataInfo)
  }

  fun insertDataRepositories(list: List<GitHubRepositoriesInfoModel>, page: Int) {
    val db = RepositoriesDatabase.getInstance(context)
    val repositoriesDao = db.repositoriesDao()

    insertDataInfo(page)

    for (item in list) {

      val model = Repositories(
        id = 0,
        name = item.name,
        stargazers_count = item.stargazersCount,
        forks_count = item.forksCount,
        login = item.owner.login,
        img_avatar = DbBitmapUtility.getBlobImage(item.owner.avatarUrl)
      )

      repositoriesDao.insertRepository(model)
    }
  }

  fun getCacheRepositories(): Observable<List<GitHubRepositoriesInfoModel>> {
    val db = RepositoriesDatabase.getInstance(context)
    val repositoriesDao = db.repositoriesDao()

    return repositoriesDao.getAllRepositories().map {
      val list: MutableList<GitHubRepositoriesInfoModel> = ArrayList()
      for (item in it) {
        val model = GitHubRepositoriesInfoModel()
        model.owner = OwnerModel()

        model.name = item.name
        model.stargazersCount = item.stargazers_count
        model.forksCount = item.forks_count
        model.owner.imgBlob = item.img_avatar
        model.owner.login = item.login

        list.add(model)
      }

      return@map list
    }
  }

  fun searchGitHubRepositories(
    query: String,
    sort: String,
    page: Int
  ): Observable<List<GitHubRepositoriesInfoModel>> {
    val retrofitClient = NetworkUtils.getRetrofitInstance(Constants.HOST)
    val gitHubRepositoriesAPI = retrofitClient.create(GitHubRepositoriesAPI::class.java)

    return gitHubRepositoriesAPI.searchRepositories(query, sort, page).map {
      insertDataRepositories(it.items, page)
      it.items
    }
  }
}