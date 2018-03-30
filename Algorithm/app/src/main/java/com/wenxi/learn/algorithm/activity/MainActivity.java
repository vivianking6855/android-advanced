package com.wenxi.learn.algorithm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wenxi.learn.algorithm.R;
import com.wenxi.learn.algorithm.adapter.AlgorithmRecyclerAdapter;
import com.wenxi.learn.algorithm.algothrim.ChineseToPinYin;
import com.wenxi.learn.algorithm.algothrim.JieTiTree;
import com.wenxi.learn.algorithm.base.BaseActivity;
import com.wenxi.learn.algorithm.model.AlgorithmModel;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends BaseActivity {
    private AlgorithmRecyclerAdapter mAdapter;
    private ExecutorService mThreadPool = Executors.newSingleThreadExecutor();

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.algorithm_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AlgorithmRecyclerAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        AlgorithmModel model = new AlgorithmModel();
        model.titleArray = Arrays.asList(getResources().getStringArray(R.array.algorithm_list));
        mAdapter.setData(model);

        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ChineseToPinYin.INSTANCE.start();
            }
        });
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                JieTiTree.INSTANCE.start();
            }
        });

    }
}
