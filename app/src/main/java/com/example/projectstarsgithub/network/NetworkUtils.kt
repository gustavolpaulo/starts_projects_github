package com.example.projectstarsgithub.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

  companion object {
    var logging = HttpLoggingInterceptor()
    fun getRetrofitInstance(path: String): Retrofit {
      logging.level = HttpLoggingInterceptor.Level.BASIC
      val httpClient = OkHttpClient.Builder()

      httpClient.addInterceptor(logging)

      return Retrofit.Builder()
        .baseUrl(path)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient.build())
        .build()
    }
  }
}