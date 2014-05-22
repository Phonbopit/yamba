package com.phonbopit.learnandroid.yamba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_tweet:
                Intent intentTweet = new Intent("com.phonbopit.learnandroid.yamba.action.tweet");
                startActivity(intentTweet);
                return true;
            case R.id.action_purge:

                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingActivity.class);
                startActivity(intentSettings);
                return true;
            case R.id.action_refresh:

                return true;
            default:
                return false;
        }

    }
}
