package com.vv.threadsync;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.vv.threadsync.current.ConcurrentCoreFeature;
import com.vv.threadsync.product.ProductManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView mTVShow;
    private DateFormat dataFormat = new SimpleDateFormat("HH:mm:ss");

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.MSG_TO_UI:
                    mTVShow.append((String) msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTVShow = (TextView) findViewById(R.id.show);
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void runSyncBasic(View v) {
        SyncBasic demo = new SyncBasic(mUIHandler);
        demo.beginTest();
    }

    public void runSyncAdvanced(View v) {
        SyncAdvanced demo = new SyncAdvanced();
        demo.beginTest();
    }

    public void runConcurrentCore(View v) {
        ConcurrentCoreFeature demo = new ConcurrentCoreFeature();
        demo.beginTest();
    }

    public void runProductConsume(View v) {
        ProductManager manager = new ProductManager();
        manager.beginTest();
    }


}
