package com.zhogolev.testapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhogolev.testapplication.model.Posts;
import com.zhogolev.testapplication.tasks.HttpRequestPostTask;
/**
 * Activity witch service post update layout
 * */
public class UpdatePostActivity extends AppCompatActivity {

    private boolean update;
    private String titleString;
    private String bodyString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        Intent intent = getIntent();
        this.titleString = intent.getStringExtra("title");
        this.bodyString = intent.getStringExtra("body");

        update = intent.getBooleanExtra("update", false);

        String buttonName = "";
        String title = "";
        if (update) {
            buttonName = "update";
            title = "update post";
        } else {
            buttonName = "save";
            title = "create new post";
        }
        setTitle(title);

        ((Button) findViewById(R.id.update_button_send)).setText(buttonName);
        ((TextView) findViewById(R.id.comment_edit_title)).setText(titleString);
        ((TextView) findViewById(R.id.comment_edit_body)).setText(bodyString);

        ((Button) findViewById(R.id.update_button_send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequestPostTask req = new HttpRequestPostTask("posts");

                try {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.zhogolev.testapplication", PackageManager.GET_META_DATA);
                    int userId = applicationInfo.metaData.getInt("user_value");
                    req.execute(new Posts(
                            ((TextView) findViewById(R.id.comment_edit_body)).getText().toString(),
                            ((TextView) findViewById(R.id.comment_edit_title)).getText().toString(),
                            0,
                            userId));

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
