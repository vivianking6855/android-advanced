package com.vv.appuniform.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vv.appuniform.R;
import com.vv.appuniform.share.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void loadData() {

    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
