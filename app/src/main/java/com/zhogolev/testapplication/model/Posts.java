package com.zhogolev.testapplication.model;

import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by konsz on 26.02.2018.
 *
 * Class for contain services for POSTS
 */

public class Posts extends Model {

    public String body;
    public String title;
    public int id;
    public int userId;

    @Override
    public String getTableName() {
        return "posts";
    }

    @Override
    public void toContentValue(ContentValues cv) {
        cv.put("id", id);
        cv.put("userId", userId);
        cv.put("body", body);
        cv.put("title", title);
    }

    public Posts(JSONObject jPost) throws JSONException {

        this.body = jPost.getString("body");
        this.id = jPost.getInt("id");
        this.userId = jPost.getInt("userId");
        this.title = jPost.getString("title");

    }

    public Posts(String body, String title, int id, int userId) {
        this.body = body;
        this.title = title;
        this.id = id;
        this.userId = userId;
    }

    @Override
    public String toString() {
        String jStringPost = "{\"body\":\"" + body + "\",\"title\":\"" + title + "\",\"id\":" + id + ", \"userId\":" + userId + "}";
        return jStringPost;
    }

    @Override
    public String stringToPost() {
        String jStringPost = "{\"body\":\"" + body + "\",\"title\":\"" + title + "\",\"userId\":" + userId + "}";
        return jStringPost;
    }
}
