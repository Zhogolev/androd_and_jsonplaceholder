package com.zhogolev.testapplication.model;

import android.content.ContentValues;

/**
 * Created by konsz on 26.02.2018.
 *
 * @see Comments
 * @see Posts
 *
 */

public abstract class Model {

    abstract public String stringToPost();

    abstract public void toContentValue(ContentValues cv);

    abstract public String getTableName();
}
