package com.dev.ome.corentin.xkcdapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class XkcdAPI {

    private val service: XkcdService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(XkcdService::class.java)
    }

    fun getComic(id: Int): Comic? {
        try {
            val call = service.getComic(id)
            val response = call.execute()
            val res = response.body()
            val bitmap = this.downloadBitmap(res!!.img)
            val stream = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageInByte = stream.toByteArray()
            res.image = imageInByte
            return res
        } catch (e: IOException) {
            return null
        }

    }

    fun downloadBitmap(urlString: String?): Bitmap? {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlString)

            connection = url.openConnection() as HttpURLConnection

            val input = BufferedInputStream(connection.inputStream)

            return BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
        return null
    }
}
