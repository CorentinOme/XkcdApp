package com.dev.ome.corentin.xkcdapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class XkcdAPI {

    private XkcdService service;

    public XkcdAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(XkcdService.class);
    }

    public Comic getComic(int id){
        try {
            Call<Comic> call = service.getComic(id);
            Response<Comic> response = call.execute();
            Comic res = response.body();
            Bitmap bitmap = this.downloadBitmap(res.getImg());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
            res.setImage(imageInByte);
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    public Bitmap downloadBitmap(String urlString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);

            connection = (HttpURLConnection) url.openConnection();

            BufferedInputStream input = new BufferedInputStream(connection.getInputStream());

            Bitmap bitmap = BitmapFactory.decodeStream(input);

            return bitmap;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
}
