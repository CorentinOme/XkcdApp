package com.dev.ome.corentin.xkcdapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by androidS4 on 28/03/18.
 */

@Database(entities = {Comic.class}, version = 1)
public abstract class XkcdDatabase extends RoomDatabase {
    public abstract ComicsDao getDao();

    private static XkcdDatabase instance;

    public static synchronized XkcdDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    XkcdDatabase.class,
                    "projects.db")
                    .build();
        }
        return instance;
    }
}
