package com.dev.ome.corentin.xkcdapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by androidS4 on 28/03/18.
 */

@Database(entities = arrayOf(Comic::class), version = 1)
abstract class XkcdDatabase : RoomDatabase() {
    abstract val dao: ComicsDao

    companion object {

        private var instance: XkcdDatabase? = null

//        @Synchronized
        fun getInstance(context: Context): XkcdDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder<XkcdDatabase>(context.applicationContext,
                        XkcdDatabase::class.java!!,
                        "projects.db")
                        .build()
            }
            return instance as XkcdDatabase
        }
    }
}
