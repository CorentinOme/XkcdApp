package com.dev.ome.corentin.xkcdapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "comic_table")
public class Comic {

    @PrimaryKey(autoGenerate = true)
    private int num;

    private String safe_title;
    private String img;
    private String transcript;
    private int day;
    private int month;
    private int year;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSafe_title() {
        return safe_title;
    }

    public void setSafe_title(String safe_title) {
        this.safe_title = safe_title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "num=" + num +
                ", safe_title='" + safe_title + '\'' +
                ", img='" + img + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
