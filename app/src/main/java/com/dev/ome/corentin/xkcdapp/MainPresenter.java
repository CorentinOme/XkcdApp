package com.dev.ome.corentin.xkcdapp;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.Random;

public class MainPresenter {
    private MainView view;
    private transient ComicsDao dao;
    private transient XkcdAPI xkcdAPI;

    private transient LiveData<PagedList<Comic>> comicsList;

    public void setView(MainView view) {
        this.view = view;
    }

    public void setDao(ComicsDao dao) {
        this.dao = dao;
        this.comicsList = new LivePagedListBuilder<>(this.dao.getAllComics(), 30)
                .build();
    }

    public void setXkcdAPI(XkcdAPI xkcdAPI) {
        this.xkcdAPI = xkcdAPI;
    }

    public void onDeleteAll() {
        this.dao.deleteAll();
    }

    public void onAdd() {
        Comic comic = xkcdAPI.getComic(new Random().nextInt(1000));
        this.view.log(comic.toString());
        this.dao.insert(comic);
    }

    public LiveData<PagedList<Comic>> getComicsList() {
        return comicsList;
    }

    public void clickItem(Comic comic){
        this.view.openOnClickItem(comic);
    }
}
