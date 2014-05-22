package com.phonbopit.learnandroid.yamba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusFragment extends Fragment {

    public static final String TAG = StatusActivity.class.getSimpleName();

    private EditText editStatus;
    private Button buttonTweet;
    private TextView textCount;
    private int defaultTextColor;

    private SharedPreferences mShared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_status, container, false);

        editStatus = (EditText) view.findViewById(R.id.editStatus);
        buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        textCount = (TextView) view.findViewById(R.id.textCount);

        defaultTextColor = textCount.getTextColors().getDefaultColor();

        editStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140 - editStatus.length();
                textCount.setText(Integer.toString(count));
                textCount.setTextColor(Color.GREEN);

                if (count < 10) {
                    textCount.setTextColor(Color.RED);
                } else {
                    textCount.setTextColor(defaultTextColor);
                }
            }
        });

        buttonTweet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = editStatus.getText().toString();
                Log.d(TAG, "onClicked with status: " + status);

                new PostTask().execute(status);
            }
        });

        return view;
    }

    private final class PostTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            mShared = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            String username = mShared.getString("username", "");
            String password = mShared.getString("password", "");

            // Check username and password are not empty.
            if (TextUtils.isEmpty(username) ||
                    TextUtils.isEmpty(password)) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
                return "Please update your username and password";
            }

            YambaClient yambaCloud = new YambaClient("student", "password");

            try {
                yambaCloud.postStatus(params[0]);
                return "Succesfully posted!!";
            } catch (YambaClientException e) {
                Log.e(TAG, e.getMessage());
                return "Failed to post to yamba service";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(StatusFragment.this.getActivity(),
                    result, Toast.LENGTH_SHORT).show();
        }
    }
}
