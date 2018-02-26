package com.zhogolev.testapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zhogolev.testapplication.adapter.CommentsListAdapter;
import com.zhogolev.testapplication.adapter.PostListAdapter;
import com.zhogolev.testapplication.helpers.DataBase;
import com.zhogolev.testapplication.helpers.General;
import com.zhogolev.testapplication.model.Comments;
import com.zhogolev.testapplication.model.Posts;
import com.zhogolev.testapplication.tasks.HttpRequestGetTask;
import com.zhogolev.testapplication.tasks.RequestGetTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

/**
 *  Activity witch services layout with comments
 * */
public class PostsActivity extends AppCompatActivity {
    private Posts post = null;
    private int userIdLocal = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setTitle("Comments");

        final ListView listView = findViewById(R.id.commentsList);
        final CommentsListAdapter adapter = new CommentsListAdapter(this.getApplicationContext(), 0);
        listView.setAdapter(adapter);

        Button updateButton = findViewById(R.id.comment_update_button);
        TextView textView = findViewById(R.id.comPostBody);

        try {
            post = new Posts(new JSONObject(getIntent().getStringExtra("post")));
            ((TextView) findViewById(R.id.comPostBody)).setText(post.body);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            userIdLocal = getPackageManager()
                    .getApplicationInfo("com.zhogolev.testapplication",
                            PackageManager.GET_META_DATA)
                    .metaData.getInt("user_value");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }


        if (post != null) {
            if (post.userId != userIdLocal) {
                updateButton.setVisibility(View.INVISIBLE);
                //textView.set;
            }
            if (General.hasConnection(this)) {
                HttpRequestGetTask req = new HttpRequestGetTask(adapter, Comments.class, this);
                req.execute("https://jsonplaceholder.typicode.com/comments?postId=" + post.id);
            } else {
                RequestGetTask req = new RequestGetTask((new DataBase(this))
                        .getWritableDatabase(), adapter, Comments.class);
                req.execute("comments", String.valueOf(post.id));
            }

        }
    }

    public void onAddCommentButtonClick(View view) {
        if (!General.hasConnection(this)) {
            General.makeNewToastAndShow(this, "No connection to internet");
            return;
        }
        startNextIntent(new Intent(PostsActivity.this, CommentsActivity.class), ButtonsMove.ADD_COMMENT);
    }

    public void onUpdateButtonClick(View view) {
        if (!General.hasConnection(this)) {
            General.makeNewToastAndShow(this, "No connection to internet");
            return;
        }
        startNextIntent(new Intent(PostsActivity.this, UpdatePostActivity.class), ButtonsMove.UPDATE_POST);
    }

    private void startNextIntent(Intent intent, ButtonsMove buttonsMove) {
        intent.putExtra("postId", post.id);
        if ((ButtonsMove.UPDATE_POST).equals(buttonsMove)) {
            intent.putExtra("body", post.body);
            intent.putExtra("title", post.title);
            intent.putExtra("update", true);
        }
        startActivity(intent);
    }

    enum ButtonsMove {
        ADD_COMMENT,
        UPDATE_POST
    }
}
