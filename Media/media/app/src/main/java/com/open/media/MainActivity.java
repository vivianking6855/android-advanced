package com.open.media;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.open.media.utils.MediaUtil;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String VIDEO_OUT_PATH = SDCARD_PATH + "/output_video4.mp4";
    private static final String AUDIO_OUT_PATH = SDCARD_PATH + "/output_audio4.mp4";
    private static final String COMBINE_OUT_PATH = SDCARD_PATH + "/combine_audio4.mp4";

    // permission request code
    private final static int PERMISSION_REQUEST_CODE_RECORD = 10000;
    // if system request dialogue show
    private boolean mSystemPermissionShowing = false;
    private AlertDialog mNeverShowDlg;

    @Override
    protected void initData() {
        mContext = this;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        findViewById(R.id.extract_media).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtil.getInstance().extractMedia(getResources().openRawResourceFd(R.raw.input), VIDEO_OUT_PATH, true);
            }
        });
        findViewById(R.id.extract_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtil.getInstance().extractMedia(getResources().openRawResourceFd(R.raw.input), AUDIO_OUT_PATH, false);
            }
        });
        findViewById(R.id.combine_media).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtil.getInstance().combineMedia(VIDEO_OUT_PATH, AUDIO_OUT_PATH, COMBINE_OUT_PATH);
            }
        });
        findViewById(R.id.combine_mp3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtil.getInstance().testConvert();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_RECORD) {
            mSystemPermissionShowing = false;
            int hasWriteStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // if user choose deny,exit app
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dealWithPermission();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNeverShowDlg != null && mNeverShowDlg.isShowing()) {
            mNeverShowDlg.dismiss();
        }
        //Log.d(TAG, "onDestroy");

    }

}
