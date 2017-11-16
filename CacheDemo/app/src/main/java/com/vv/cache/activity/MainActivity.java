package com.vv.cache.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vv.cache.R;
import com.vv.cache.base.BaseActivity;
import com.vv.cache.cache.DiskCacheManager;
import com.vv.cache.cache.GlobalManager;
import com.vv.cache.listener.IDownloadListener;
import com.vv.cache.model.DataModel;
import com.vv.cache.presenter.DownloadPresenter;

import java.util.List;

public class MainActivity extends BaseActivity implements IDownloadListener {
    private final static String TAG = "MainActivity";

    // presenter of MVP
    private DownloadPresenter mPresenter;

    private Context mContext;
    private TextView mHint;

    // permission request code
    private final static int PERMISSION_REQUEST_CODE_RECORD = 10000;
    // if system request dialogue show
    private boolean mSystemPermissionShowing = false;
    private AlertDialog mNeverShowDlg;

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
                mHint.setText(GlobalManager.INSTANCE.mDataCount + ", b: " +  mPresenter.getFromCache("b"));
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
    protected void onResume() {
        super.onResume();
        dealWithPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNeverShowDlg != null && mNeverShowDlg.isShowing()) {
            mNeverShowDlg.dismiss();
        }

        mPresenter.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_RECORD) {
            mSystemPermissionShowing = false;
            int hasWriteStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // if user choose deny,exit app
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void dealWithPermission() {
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "permission not granted");
            // if user choose never show before, request system permission will not work
            boolean never_show = !ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (never_show) {
                Log.d(TAG, "user choose never show");
                showNeverShowHintDialogue();
            } else {
                if (!mSystemPermissionShowing) {
                    Log.d(TAG, "show system permission dialogue");
                    // show system permission dialogue
                    mSystemPermissionShowing = true;
                    showSystemRequestDialog();
                }
            }
        } else {
            mPresenter.loadData();
        }
    }

    private void showSystemRequestDialog() {
        // show system permission dialogue
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE_RECORD);
    }

    private void showNeverShowHintDialogue() {
        mNeverShowDlg = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle(getString(R.string.grant_permission_title))
                .setMessage(getString(R.string.grant_permission))
                .setPositiveButton(getString(R.string.grant_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user choose never show,you could change your resolution here for your project
                        gotoAppDetail();
                    }
                })
                .setNegativeButton(getString(R.string.grant_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void gotoAppDetail() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

}
