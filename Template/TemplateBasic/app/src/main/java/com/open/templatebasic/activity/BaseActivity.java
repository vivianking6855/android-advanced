package com.open.templatebasic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Use it like this: setContentView must before super.onCreate
 * public class MainActivity extends BaseActivity {
 *  @Override protected void onCreate(Bundle savedInstanceState) {
 *      setContentView(R.layout.activity_main);
 *      super.onCreate(savedInstanceState);
 *  }
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView(savedInstanceState);
        loadData();
    }

    /**
     * Init data.
     */
    protected abstract void initData();

    /**
     * Init view.
     * please setContentView here
     *
     * @param savedInstanceState the saved instance state
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * Load data.
     */
    protected abstract void loadData();

}

