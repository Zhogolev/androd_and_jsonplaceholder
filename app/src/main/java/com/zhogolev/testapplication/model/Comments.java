package com.zhogolev.testapplication.model;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by konsz on 26.02.2018.
 *
 *Class for contain services for Comments
 */

public class Comments extends Model{

    public String body;
    public String name;
    public int postId;
    public int id;

    @Override
    public String getTableName() {
        return "comments";
    }

    @Override
    public void toContentValue(ContentValues cv) {
        cv.put("body", body);
        cv.put("name",name);
        cv.put("postId", postId);
        cv.put("id", id);
    }

    public Comments(JSONObject jPost) throws JSONException {

        this.body = jPost.getString("body");
        this.name = jPost.getString("name");
        this.id = jPost.getInt("id");
        this.postId = jPost.getInt("postId");
    }

    public Comments(String body, String name, int postId) {
        this.body = body;
        this.name = name;
        this.postId = postId;
    }

    public Comments(String body, String name, int postId, int id) {
        this.body = body;
        this.name = name;
        this.postId = postId;
        this.id = id;
    }

    @Override
    public String toString() {
        String jStringPost = "{\"body\": " + body + ",\"name\": " + name + ",\"id\" :" + id + ", \"postId\" :" + postId + "}";
        return jStringPost;
    }

    @Override
    public String stringToPost() {
        String jStringPost = "{\"body\" :" + body + ",\"name\" :" + name + ", \"postId\" :" + postId + "}";
        return jStringPost;
    }
}
