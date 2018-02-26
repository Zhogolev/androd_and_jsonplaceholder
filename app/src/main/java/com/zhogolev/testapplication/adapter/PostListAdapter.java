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
import com.zhogolev.testapplication.model.Model;
import com.zhogolev.testapplication.model.Posts;

/**
 * Created by konsz on 23.02.2018.
 */

public class PostListAdapter  extends ArrayAdapter<Model>{

    Context context;

    public PostListAdapter(@NonNull Context context, int resource) {
        super(context, R.layout.post);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate(R.layout.post, null);
        if (getItem(position) instanceof Posts) {
            ((TextView) view.findViewById(R.id.userId)).setText(((Posts) getItem(position)).userId + "");
            ((TextView) view.findViewById(R.id.post)).setText(((Posts) getItem(position)).body);
            ((TextView) view.findViewById(R.id.title)).setText(((Posts) getItem(position)).title);
        }
        return view;
    }
}
