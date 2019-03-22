package com.dev.ome.corentin.xkcdapp;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface ComicsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comic... comic);

    @Query("SELECT * FROM comic_table")
    DataSource.Factory<Integer, Comic> getAllComics();

    @Query("DELETE FROM comic_table")
    void deleteAll();
}
