package com.zhogolev.testapplication.tasks;

import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import com.zhogolev.testapplication.helpers.General;
import com.zhogolev.testapplication.model.Model;
import com.zhogolev.testapplication.model.Posts;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by konsz on 26.02.2018.
 */

public class HttpRequestPostTask extends AsyncTask<Model, Void, Void> {

    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private static String url = "https://jsonplaceholder.typicode.com/";

    public HttpRequestPostTask(String pathToPost) {
           if(pathToPost.equals(""))
               pathToPost = "posts";

            url += pathToPost;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.w(TAG, "onPreExecute: END EXEC POST REQUEST");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.w(TAG, "onPreExecute: START PRE EXEC POST REQUEST");
    }

    @Override
    protected Void doInBackground(Model... posts) {

        Model localPost =  posts[0];

        try {

            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            writer.write(localPost.stringToPost());
            //writer.flush();
            writer.close();
            connection.connect();


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.d("Post to site", "doInBackground:  post complited");
        }

        return null;
    }

}
