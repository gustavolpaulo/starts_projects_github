package com.example.projectstarsgithub.service

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.util.Log
import com.example.projectstarsgithub.costants.Constants
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class DbBitmapUtility {

  companion object {
    fun getBytes(bitmap: Bitmap): ByteArray? {
      val stream = ByteArrayOutputStream()
      bitmap.compress(CompressFormat.PNG, 0, stream)
      val value = stream.toByteArray()
      stream.close()
      return value
    }

    fun getBlobImage(avatar_ulr: String): ByteArray? {
      try {
        val url = URL(avatar_ulr)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        return getBytes(BitmapFactory.decodeStream(input))

      } catch (e: IOException) {
        Log.e(Constants.REPOSITORIES_VIEW_MODEL, e.message.toString())
      }
      return null
    }

  }
}