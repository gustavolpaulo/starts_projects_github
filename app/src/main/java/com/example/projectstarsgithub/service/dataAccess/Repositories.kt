package com.example.projectstarsgithub.service.dataAccess

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repositories(
  @PrimaryKey(autoGenerate = true)
  val id: Int,

  @ColumnInfo(name = "name")
  val name: String,

  @ColumnInfo(name = "stargazers_count")
  val stargazers_count: Int,

  @ColumnInfo(name = "forks_count")
  val forks_count: Int = 0,

  @ColumnInfo(name = "img_avatar")
  val img_avatar: ByteArray? = null,

  @ColumnInfo(name = "login")
  val login: String

)