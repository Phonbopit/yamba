package com.phonbopit.learnandroid.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by creatived on 5/22/14.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
    }

    // Called only one first time we create the database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = String
                .format("create table %s (%s int primary key, %s text, %s text, %s int)",
                        StatusContract.TABLE,
                        StatusContract.Column.ID,
                        StatusContract.Column.USER,
                        StatusContract.Column.MESSAGE,
                        StatusContract.Column.CREATED_AT);

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "drop table if exists " + StatusContract.TABLE;

        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
