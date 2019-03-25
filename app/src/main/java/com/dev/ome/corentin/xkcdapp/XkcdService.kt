package com.dev.ome.corentin.xkcdapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface XkcdService {
    @GET("{id}/info.0.json")
    fun getComic(@Path("id") comicId: Int): Call<Comic>
}
