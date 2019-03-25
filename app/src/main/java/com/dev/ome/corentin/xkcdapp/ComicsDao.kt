package com.dev.ome.corentin.xkcdapp

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface ComicsDao {

    @get:Query("SELECT * FROM comic_table")
    val allComics: DataSource.Factory<Int, Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg comic: Comic)

    @Query("DELETE FROM comic_table")
    fun deleteAll()
}
