package com.learn.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.open.appbase.activity.BaseActivity;
import com.open.appbase.adapter.recyclerview.RecyclerArrayAdapter;
import com.open.appbase.adapter.recyclerview.RecyclerItemClickListener;
import com.learn.algorithm.R;
import com.learn.algorithm.algothrim.AlgorithmContext;
import com.learn.algorithm.algothrim.AlgorithmFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learn.algorithm.utils.Const.LOG_TAG;


public class MainActivity extends BaseActivity {
    // work thread pool
    private ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    // algorithm context and factory 策略模式和工厂模式
    private AlgorithmContext mStrategy;
    private AlgorithmFactory algorithmFactory;

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
        mStrategy = AlgorithmContext.getInstance();
        algorithmFactory = AlgorithmFactory.getInstance();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.algorithm_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerArrayAdapter(this,
                getResources().getStringArray(R.array.algorithm_list)));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(LOG_TAG, "onItemClick " + position);
                mStrategy.setAlgorithm(algorithmFactory.createAlgorithm(position));
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        mStrategy.startAlgorithm();
                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        algorithmFactory.destroy();
    }
}
