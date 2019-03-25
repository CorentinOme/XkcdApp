package com.dev.ome.corentin.xkcdapp

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "comic_table")
class Comic {

    @PrimaryKey(autoGenerate = true)
    var num: Int = 0

    var safe_title: String? = null
    var img: String? = null
    var transcript: String? = null
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null

    override fun toString(): String {
        return "Comic{" +
                "num=" + num +
                ", safe_title='" + safe_title + '\''.toString() +
                ", img='" + img + '\''.toString() +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}'.toString()
    }
}
