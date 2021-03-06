package com.phonbopit.learnandroid.yamba;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;

public class RefreshService extends IntentService {

    public static final String TAG = RefreshService.class.getSimpleName();

    public RefreshService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please update your username and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        YambaClient client = new YambaClient(username, password);

        try {
            int count = 0;
            List<YambaClient.Status> timeline = client.getTimeline(20);
            for (YambaClient.Status status : timeline) {
                values.clear();
                values.put(StatusContract.Column.ID, status.getId());
                values.put(StatusContract.Column.USER, status.getUser());
                values.put(StatusContract.Column.MESSAGE, status.getMessage());
                values.put(StatusContract.Column.CREATED_AT, status.getCreatedAt().getTime());

                Uri uri = getContentResolver().insert(
                        StatusContract.CONTENT_URI, values);

                if (uri != null) {
                    count++;
                    String s = String.format("%s: %s", status.getUser(), status.getMessage());
                    Log.i(TAG, s);
                }

/*                db.insertWithOnConflict(StatusContract.TABLE, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);*/
            }
        } catch (YambaClientException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
    }
}
