package com.zhogolev.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhogolev.testapplication.adapter.PostListAdapter;
import com.zhogolev.testapplication.helpers.DataBase;
import com.zhogolev.testapplication.helpers.General;
import com.zhogolev.testapplication.model.Posts;
import com.zhogolev.testapplication.tasks.HttpRequestGetTask;
import com.zhogolev.testapplication.tasks.RequestGetTask;
/**
 * Main activity starting on launch app
 * */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.posts);
        final PostListAdapter adapter = new PostListAdapter(this.getApplicationContext(), 0);
        listView.setAdapter(adapter);
        if (General.hasConnection(this)) {
            HttpRequestGetTask req = new HttpRequestGetTask(adapter, Posts.class, this);
            req.execute("https://jsonplaceholder.typicode.com/posts");
        }else {
            RequestGetTask req = new RequestGetTask((new DataBase(this)).getWritableDatabase(),adapter, Posts.class);
            req.execute("posts");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Posts post = (Posts) adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                intent.putExtra("post", post.toString());
                startActivity(intent);

            }
        });

    }

    public void onClickMainButton(View view) {
        if (!General.hasConnection(this)){
            General.makeNewToastAndShow(this,"No connection to internet");
            return;
        }
        Intent intent = new Intent(MainActivity.this, UpdatePostActivity.class);
        intent.putExtra("title", "");
        intent.putExtra("body", "");
        intent.putExtra("update", false);
        startActivity(intent);

    }
}


