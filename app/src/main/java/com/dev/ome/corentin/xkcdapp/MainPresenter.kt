package com.dev.ome.corentin.xkcdapp

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList

import java.util.Random

class MainPresenter {
    private var view: MainView? = null

    private var dao: ComicsDao? = null

    private var xkcdAPI: XkcdAPI? = null


    var comicsList: LiveData<PagedList<Comic>>? = null
        private set

    fun setView(view: MainView) {
        this.view = view
    }

    fun setDao(dao: ComicsDao) {
        this.dao = dao
        this.comicsList = LivePagedListBuilder(this.dao!!.allComics, 30)
                .build()
    }

    fun setXkcdAPI(xkcdAPI: XkcdAPI) {
        this.xkcdAPI = xkcdAPI
    }

    fun onDeleteAll() {
        this.dao!!.deleteAll()
    }

    fun onAdd() {
        val comic = xkcdAPI!!.getComic(Random().nextInt(1000))
        this.view!!.log(comic!!.toString())
        this.dao!!.insert(comic)
    }

    fun clickItem(comic: Comic) {
        this.view!!.openOnClickItem(comic)
    }
}
