package com.open.media;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
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
    private boolean mPermissionShowing = false;

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
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_RECORD) {
            int hasWriteStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                boolean nevershow = !shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (nevershow) {
                    showPermissionDlg();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showPermissionDlg() {
        if (!mPermissionShowing) {
            mPermissionShowing = true;
            new AlertDialog.Builder(mContext)
                    .setCancelable(false)
                    .setTitle(getString(R.string.grant_permission_title))
                    .setMessage(getString(R.string.grant_permission))
                    .setPositiveButton(getString(R.string.grant_know), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private void checkPermission() {
        int hasWriteStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // show system permission dialogue
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE_RECORD);
        }
    }
}
