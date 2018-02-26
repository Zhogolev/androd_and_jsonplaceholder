package com.zhogolev.testapplication.tasks;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.zhogolev.testapplication.MainActivity;
import com.zhogolev.testapplication.adapter.PostListAdapter;
import com.zhogolev.testapplication.model.Comments;
import com.zhogolev.testapplication.model.Model;
import com.zhogolev.testapplication.model.Posts;

import static android.content.ContentValues.TAG;

/**
 * Created by konsz on 26.02.2018.
 */

public class RequestGetTask extends AsyncTask<String, Void, Void> {
    private SQLiteDatabase sqlDB;
    private ArrayAdapter<Model> adapter;
    private Class resultClass;

    public RequestGetTask(SQLiteDatabase sqlDB, ArrayAdapter<Model> adapter, Class resultClass) {
        this.sqlDB = sqlDB;
        this.adapter = adapter;
        this.resultClass = resultClass;
    }

    @Override
    protected Void doInBackground(String... strings) {

        //Log.w(TAG, "doInBackground: START GETTING DATA" );
        //if no params exist back
        if (strings.length == 0)
            return null;

        String tableName = strings[0];

        Cursor queryResult = sqlDB.query(tableName, null, null, null, null, null, null);

        int valueOfPostId = 0;

        if (strings.length > 2) {
            valueOfPostId = Integer.parseInt(strings[1]);
            queryResult = sqlDB.query(tableName, null, "postId=?", new String[]{String.valueOf(valueOfPostId)}, null, null, null);
        }
        while (queryResult.moveToNext()) {
            Model model = null;
            if (resultClass.equals(Posts.class)) {
                model = new Posts(
                        queryResult.getString(queryResult.getColumnIndex("body")),
                        queryResult.getString(queryResult.getColumnIndex("title")),
                        queryResult.getInt(queryResult.getColumnIndex("id")),
                        queryResult.getInt(queryResult.getColumnIndex("userId"))

                );


            } else if (resultClass.equals(Comments.class)) {
                model = new Comments(
                        queryResult.getString(queryResult.getColumnIndex("body")),
                        queryResult.getString(queryResult.getColumnIndex("name")),
                        queryResult.getInt(queryResult.getColumnIndex("postId")),
                        queryResult.getInt(queryResult.getColumnIndex("id"))
                );
            }
            //Log.w(TAG, "Get data " + model.stringToPost());
            adapter.add(model);

        }
        return null;
    }
}
