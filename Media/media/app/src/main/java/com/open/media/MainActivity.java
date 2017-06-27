package com.open.media;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.open.media.utils.MediaUtil;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String VIDEO_OUT_PATH = SDCARD_PATH + "/output_video4.mp4";
    private static final String AUDIO_OUT_PATH = SDCARD_PATH + "/output_audio4.mp4";
    private static final String COMBINE_OUT_PATH = SDCARD_PATH + "/combine_audio4.mp4";

    @Override
    protected void initData() {

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


}
