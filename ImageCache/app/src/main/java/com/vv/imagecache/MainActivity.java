package com.vv.imagecache;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

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
        String[] imageUrls = {
                "http://img5.imgtn.bdimg.com/it/u=1832793441,1411325684&fm=23&gp=0.jpg",
                "http://img5.imgtn.bdimg.com/it/u=4221822160,1841690567&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=2759025002,80167188&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=1112164777,2008686483&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=1487846645,638899586&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=2774622356,380615814&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=763680772,801917499&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=2973819139,278683845&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=3555374458,1355994284&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=3451117690,1557873649&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=2504533227,2722514678&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=683490491,1388956479&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=2116881695,1396251290&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=2427772872,476067364&fm=23&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=2823268753,508707860&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=2205206959,2336608631&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=1540190318,3092669835&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=2712765120,4118755937&fm=23&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=2750417917,2621359041&fm=23&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3909198784,483043653&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=2298319393,3442753978&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=3543888135,2537441435&fm=23&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3393518603,928662740&fm=23&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3489019248,357613167&fm=23&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=3355943974,744316730&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=3897787048,3830466614&fm=23&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=1696580889,1090546761&fm=23&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3380854502,1889576381&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=329514161,3334869905&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1447132931,2716990365&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=845158990,382093692&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=547603263,3145253368&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1022941267,1075280551&fm=23&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=867850698,2896049004&fm=23&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=1978632810,2186123622&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=3896485117,1959423425&fm=23&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=4004728073,322962386&fm=23&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=3419504518,3833289618&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=4049571105,703575195&fm=23&gp=0.jpg",
                "http://img2.imgtn.bdimg.com/it/u=2798280900,2608463244&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=139814970,2084708977&fm=23&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=2348275693,563411762&fm=23&gp=0.jpg",
                "http://img1.imgtn.bdimg.com/it/u=2854415857,1672964572&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=2020995193,4186176601&fm=23&gp=0.jpg",
                "http://img4.imgtn.bdimg.com/it/u=139170882,1374024010&fm=23&gp=0.jpg",
        };
        for (String url : imageUrls) {
            Utils.sUrList.add(url);
        }
        int screenWidth = Utils.getScreenMetrics(this).widthPixels;
        int space = (int) Utils.dp2px(this, 20f);
        Utils.sImageWidth = (screenWidth - space) / 3;
        mIsWifi = Utils.isWifi(this);
        if (mIsWifi) {
            Utils.sCanGetBitmapFromNetWork = true;
        }

        mImageAdapter = new ImageAdapter(this);
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
