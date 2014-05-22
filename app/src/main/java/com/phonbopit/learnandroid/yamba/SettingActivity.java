package com.phonbopit.learnandroid.yamba;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            SettingsFragment fragment = new SettingsFragment();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment, "SettingsFragment")
                    .commit();
        }
    }
}
