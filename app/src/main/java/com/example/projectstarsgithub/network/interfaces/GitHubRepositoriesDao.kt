package com.example.projectstarsgithub.network.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projectstarsgithub.service.dataAccess.DataInfo
import com.example.projectstarsgithub.service.dataAccess.Repositories
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface GitHubRepositoriesDao {

  @Query("SELECT * FROM Repositories")
  fun getAllRepositories(): Observable<List<Repositories>>

  @Insert
  fun insertRepository(vararg repository: Repositories)

  @Query("DELETE FROM Repositories")
  fun clearTbRepositories()

  @Query("SELECT * FROM DataInfo")
  fun getDataInfo(): Single<DataInfo?>

  @Insert
  fun insertDatainfo(vararg dataInfo: DataInfo)

  @Query("DELETE FROM DataInfo")
  fun clearTbDataInfo()

}