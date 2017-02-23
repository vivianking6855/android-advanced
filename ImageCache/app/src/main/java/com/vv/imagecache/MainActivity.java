package com.vv.imagecache;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.vv.imagecache.common.Const;
import com.vv.imagecache.common.Utils;
import com.vv.imagecache.ui.ImageAdapter;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
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

    private void initData() {
        for (String url : Const.IMG_URLS) {
            Utils.sUrList.add(url);
        }
        int screenWidth = Utils.getScreenMetrics(this).widthPixels;
        int space = (int) Utils.dp2px(this, 20f);
        Utils.sImageWidth = (screenWidth - space) / 3;
        mIsWifi = Utils.isWifi(this);
        if (mIsWifi) {
            Utils.sCanGetBitmapFromNetWork = true;
        }
    }

    private void initView() {
        mImageGridView = (GridView) findViewById(R.id.gv_images);
        mImageAdapter = new ImageAdapter(this);
        mImageGridView.setAdapter(mImageAdapter);
        mImageGridView.setOnScrollListener(this);

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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            Utils.sIsGridViewIdle = true;
            mImageAdapter.notifyDataSetChanged();
        } else {
            Utils.sIsGridViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // ignored
    }

}
