package com.zhogolev.testapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zhogolev.testapplication.R;
import com.zhogolev.testapplication.model.Comments;
import com.zhogolev.testapplication.model.Model;
import com.zhogolev.testapplication.model.Posts;

/**
 * Created by konsz on 24.02.2018.
 */

public class CommentsListAdapter extends ArrayAdapter<Model> {

    Context context;

    public CommentsListAdapter(@NonNull Context context, int resource) {
        super(context, R.layout.post);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate(R.layout.comments, null);
        if (getItem(position) != null) {
            ((TextView) view.findViewById(R.id.commentBody)).setText(((Comments) getItem(position)).body);
            ((TextView) view.findViewById(R.id.commentName)).setText(((Comments) getItem(position)).name);
        }
        return view;
    }
}
