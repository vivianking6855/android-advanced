package com.vv.performancedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
