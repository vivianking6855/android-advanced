package com.open.webdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.open.webdemo.R;
import com.open.webdemo.engine.WebsiteEngine;
import com.open.webdemo.entity.SearchEvent;
import com.open.webdemo.entity.UrlConfigEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTVShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTVShow = (TextView) findViewById(R.id.tv_show);
    }

    public void okhttpConfigClick(View view) {
        WebsiteEngine.getInstance().getConfig();
    }

    public void okhttpSearchClick(View view) {
        WebsiteEngine.getInstance().search();
    }

    public void retrofitClick(View view) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UrlConfigEvent event) {
        mTVShow.setText(new Gson().toJson(event.mEntity.Default));
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        mTVShow.setText(new Gson().toJson(event.mEntityList));
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
