package com.example.projectstarsgithub.service.dataAccess

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataInfo(

  @PrimaryKey(autoGenerate = true)
  var id: Int,

  @ColumnInfo(name = "initData")
  var initData: String,

  @ColumnInfo(name = "lastPage")
  var lastPage: Int = 0
)