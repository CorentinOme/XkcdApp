package com.dev.ome.corentin.xkcdapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XkcdService {
    @GET("{id}/info.0.json")
    Call<Comic> getComic(@Path("id") int comicId);
}
