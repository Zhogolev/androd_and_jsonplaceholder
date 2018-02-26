package com.zhogolev.testapplication.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zhogolev.testapplication.adapter.PostListAdapter;
import com.zhogolev.testapplication.helpers.DataBase;
import com.zhogolev.testapplication.model.Model;
import com.zhogolev.testapplication.model.Posts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by konsz on 26.02.2018.
 * <p>
 * This class for get all posts and put them into layout
 */

public class HttpRequestGetTask extends AsyncTask<String, Void, Void> {

    private String result;
    private ArrayAdapter<Model> adapter;
    private Class resultClass;
    private Context context;

    public HttpRequestGetTask(ArrayAdapter<Model> adapter, Class resultClass, Context context) {
        this.resultClass = resultClass;
        this.adapter = adapter;
        this.context = context;
    }

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            DataBase db = new DataBase(context);
            SQLiteDatabase sdb = db.getWritableDatabase();

            JSONArray jsonPostsArray = new JSONArray(this.result);
            for (int i = 0; i < jsonPostsArray.length(); i++) {
                Model model = (Model) resultClass.getDeclaredConstructor(JSONObject.class).newInstance(jsonPostsArray.getJSONObject(i));
                adapter.add(model);
                ContentValues cv = new ContentValues();
                model.toContentValue(cv);
                sdb.replace(model.getTableName(), null, cv);
                Log.w(TAG, "onPostExecute: SAVING DATA " + model.stringToPost());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected Void doInBackground(String... strings) {
        String stringUrl = strings[0];
        String result = "";
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Content-type", "application/json");
            connection.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();

            this.result = stringBuilder.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

