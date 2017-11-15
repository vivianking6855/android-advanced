package com.vv.cache.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vv.cache.R;
import com.vv.cache.base.BaseActivity;
import com.vv.cache.cache.DiskCacheManager;
import com.vv.cache.listener.IDownloadListener;
import com.vv.cache.model.DataModel;
import com.vv.cache.presenter.DownloadPresenter;

import java.util.List;

public class MainActivity extends BaseActivity implements IDownloadListener {

    // presenter of MVP
    private DownloadPresenter mPresenter;

    private Context mContext;
    private TextView mHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        mContext = this;
        mPresenter = new DownloadPresenter(this);
        mPresenter.setListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mHint = (TextView)findViewById(R.id.tv_hint);

        findViewById(R.id.tv_lru).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.tv_disk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHint.setText(mPresenter.getFromDiskCache("c"));
            }
        });
        findViewById(R.id.tv_common).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void loadData() {
        mPresenter.loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        DiskCacheManager.INSTANCE.flushDiskLruCache();
    }

    @Override
    public void OnLoadStart() {

    }

    @Override
    public void OnLoadSuccess(List<DataModel> data) {
        Toast.makeText(mContext, "OnLoadSuccess " + data.size(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnLoadFail(String error) {

    }

}
