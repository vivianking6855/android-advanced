package com.vv.frescodemo;

import android.content.DialogInterface;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.vv.frescodemo.common.Const;
import com.vv.frescodemo.common.Utils;
import com.vv.frescodemo.ui.ImageAdapter;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private GridView mImageGridView;
    private BaseAdapter mImageAdapter;
    private boolean mIsWifi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        mImageGridView = (GridView) findViewById(R.id.gv_images);
        mImageAdapter = new ImageAdapter(this);
        mImageGridView.setAdapter(mImageAdapter);

        if (!mIsWifi) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("初次使用会从网络下载大概5MB的图片，确认要下载吗？");
            builder.setTitle("注意");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Utils.sCanGetBitmapFromNetWork = true;
                    mImageAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("否", null);
            builder.show();
        }
    }

    private void initData() {
        for (String url : Const.IMG_URLS) {
            Utils.sUrList.add(url);
        }

        int screenWidth = Utils.getScreenMetrics(this).widthPixels;
        Utils.sImageWidth = (screenWidth) / 3;
        mIsWifi = Utils.isWifi(this);
        if (mIsWifi) {
            Utils.sCanGetBitmapFromNetWork = true;
        }
    }


}
