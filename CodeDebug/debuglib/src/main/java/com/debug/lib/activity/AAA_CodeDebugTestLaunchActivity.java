package com.debug.lib.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class AAA_CodeDebugTestLaunchActivity extends Activity {
    private TextView rootView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(rootView);
    }

    private void initView() {
        rootView = (TextView) View.inflate(this, android.R.layout.browser_link_context_header, null);
        rootView.setText("AAA_CodeDebugTestLaunchActivity");
        rootView.setTextColor(Color.RED);
        rootView.setGravity(Gravity.CENTER);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomePage();
            }
        });
    }

    private void launchHomePage() {
        //Intent intent = new Intent(AAA_CodeDebugTestLaunchActivity.this, MainDialtactsActivity.class);
        //startActivity(intent);
    }
}

