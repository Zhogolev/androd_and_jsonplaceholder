package com.zhogolev.testapplication.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by konsz on 26.02.2018.
 */

public class DataBase extends SQLiteOpenHelper {

    private static final String name = "testAppDataBase";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 1;

    public DataBase(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Table for contain posts from web
        db.execSQL("create table if not exists posts (id integer primary key, title text, body text, userId integer)");
        //Table for contain comments
        db.execSQL("create table if not exists comments (postId integer primary key, name text, body text, id integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //i hope u will not change my tables...
    }
}
