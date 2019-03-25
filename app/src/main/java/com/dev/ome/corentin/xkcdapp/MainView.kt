package com.dev.ome.corentin.xkcdapp

interface MainView {
    fun log(message: String)

    fun openOnClickItem(comic: Comic)
}
