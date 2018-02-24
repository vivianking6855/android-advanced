package com.vv.cache.activity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vv.cache.R;
import com.vv.cache.base.BasePermissionActivity;
import com.vv.cache.cache.DiskCacheManager;
import com.vv.cache.cache.GlobalManager;
import com.vv.cache.listener.IDownloadListener;
import com.vv.cache.model.DataModel;
import com.vv.cache.presenter.DownloadPresenter;

import java.util.List;

public class MainActivity extends BasePermissionActivity implements IDownloadListener {
    private final static String TAG = "MainActivity";

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

        mHint = (TextView) findViewById(R.id.tv_hint);
        mHint.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.tv_lru).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHint.setText(mPresenter.getFromLruCache("a"));
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
                mHint.setText(GlobalManager.INSTANCE.mDataCount + ", b: " + mPresenter.getFromCache("b"));
            }
        });

    }

    @Override
    protected void loadData() {

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

    @Override
    protected void permissionGranted() {
        mPresenter.loadData();
    }

    @Override

    protected void permissionDeny() {
        finish();
    }

    @Override
    protected String[] getPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

}
