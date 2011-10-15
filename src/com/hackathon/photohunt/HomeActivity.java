package com.hackathon.photohunt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Log.d("hello", "change");
    }
}