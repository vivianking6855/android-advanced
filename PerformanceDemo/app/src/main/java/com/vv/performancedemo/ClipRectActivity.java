package com.vv.performancedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ClipRectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_rect);

        getWindow().setBackgroundDrawable(null);
    }
}
