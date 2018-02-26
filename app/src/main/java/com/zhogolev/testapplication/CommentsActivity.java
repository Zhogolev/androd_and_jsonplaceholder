package com.zhogolev.testapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zhogolev.testapplication.helpers.General;
import com.zhogolev.testapplication.model.Comments;
import com.zhogolev.testapplication.tasks.HttpRequestPostTask;
/**
 * Activity witch services activity_add_comment layout
 * */
public class CommentsActivity extends AppCompatActivity {

    final int DEFAULT_VALUE_OF_POST_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        setTitle("New comment");
    }

    public void onSendButtonClick(View view) {

        int postId = getIntent().getIntExtra("postId", DEFAULT_VALUE_OF_POST_ID);
        String name = ((TextView) findViewById(R.id.comment_edit_title)).getText().toString();
        String body = ((TextView) findViewById(R.id.comment_edit_body)).getText().toString();
        String mail = ((TextView) findViewById(R.id.comment_edit_mail)).getText().toString();

        if((name.equals("") || body.equals("") || mail.equals("")) && postId != DEFAULT_VALUE_OF_POST_ID ){
            General.makeNewToastAndShow(getApplicationContext(), "information for sending msg is not full post id" + postId);
            return;
        }

        HttpRequestPostTask req = new HttpRequestPostTask("comments");
        req.execute(new Comments(body,name,postId));

    }
}
