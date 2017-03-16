package com.vv.performancedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.vv.performancedemo.controller.HomeAdapter;

public class MainActivity extends AppCompatActivity implements HomeAdapter.OnItemClickLitener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main_bad);

        getWindow().setBackgroundDrawable(null);

        initView();
    }

    private void initView() {
        // init recycler view
        recyclerView = (RecyclerView) findViewById(R.id.homelist);
        homeAdapter = new HomeAdapter(this);
        homeAdapter.setOnItemClickLitener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick position: " + position);
        switch (position) {
            case 0:
                startActivity(ClipRectActivity.class);
                break;
            case 1:
                startActivity(HVOptimizeActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, cls);
        startActivity(intent);
    }

}
