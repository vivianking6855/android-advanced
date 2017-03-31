package com.vv.performancedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.vv.performancedemo.common.ImageUtils;

public class HVOptimizeActivity extends AppCompatActivity {

    private View offlineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvoptimize);

        initView();
    }

    private void initView() {
        ImageView avatar1 = (ImageView) findViewById(R.id.avatar1);
        ImageView avatar2 = (ImageView) findViewById(R.id.avatar2);
        avatar1.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.card1, 100, 100));
        avatar2.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.card2, 100, 100));
    }

    public void showOffline(View v) {
        if (offlineView == null) {
            ViewStub noDataViewStub = (ViewStub) findViewById(R.id.offline_view);
            offlineView = noDataViewStub.inflate();
        } else {
            offlineView.setVisibility(View.VISIBLE);
        }
    }

    public void hideOffline(View v) {
        if (offlineView != null) {
            offlineView.setVisibility(View.INVISIBLE);
        }
    }

}
