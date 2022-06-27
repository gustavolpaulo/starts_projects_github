package com.example.projectstarsgithub.models

import com.google.gson.annotations.SerializedName

class OwnerModel {

  @SerializedName("avatar_url")
  lateinit var avatarUrl: String

  @SerializedName("login")
  lateinit var login: String

  var imgBlob: ByteArray? = null
}