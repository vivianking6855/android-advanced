package com.vv.ipc;

import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private final static String TAG = SecondActivity.class.getSimpleName();

    private TextView mTVShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
    }

    private void initView() {
        mTVShow = (TextView) findViewById(R.id.show);
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String msg = getIntent().getStringExtra(Const.KEY_TO_SECOND);
        if (msg != null) {
            String txt = msg + "; myPid = " + Process.myPid() + "; sUserId = " + UserManager.sUserId;
            mTVShow.setText(txt);
        }
    }
}
