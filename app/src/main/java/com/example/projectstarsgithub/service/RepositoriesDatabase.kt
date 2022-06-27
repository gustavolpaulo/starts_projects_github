package com.example.projectstarsgithub.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectstarsgithub.network.interfaces.GitHubRepositoriesDao
import com.example.projectstarsgithub.service.dataAccess.DataInfo
import com.example.projectstarsgithub.service.dataAccess.Repositories

@Database(entities = [Repositories::class, DataInfo::class], exportSchema = false, version = 1)
abstract class RepositoriesDatabase : RoomDatabase() {

  companion object {
    private var instance: RepositoriesDatabase? = null

    const val DB_NAME = "repositories_db"

    fun getInstance(context: Context): RepositoriesDatabase {
      if (instance == null) {
        instance = Room.databaseBuilder(
          context,
          RepositoriesDatabase::class.java, DB_NAME
        ).build()
      }
      return instance!!
    }
  }

  abstract fun repositoriesDao(): GitHubRepositoriesDao

}