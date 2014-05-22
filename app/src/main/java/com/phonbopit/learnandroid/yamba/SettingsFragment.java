package com.phonbopit.learnandroid.yamba;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences mShared;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onStart() {
        super.onStart();
        mShared = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mShared.registerOnSharedPreferenceChangeListener
                (new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged
                    (SharedPreferences sharedPreferences, String key) {

            }
        });
    }

}
