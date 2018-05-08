package com.demo.loader.activity;

import android.os.SystemClock;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.demo.loader.R;
import com.demo.loader.applist.ApkEntity;
import com.demo.loader.applist.ApkListAdapter;
import com.demo.loader.applist.ApkListLoader;
import com.demo.loader.common.Const;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

public class ApkListActivity extends AppCompatActivity {
    private Reference<ApkListActivity> mActivityRef;

    private ApkListAdapter mApkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_list);

        mActivityRef = new WeakReference<>(this);

        initView();
        initData();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivityRef.get()));
        recyclerView.setAdapter(mApkAdapter = new ApkListAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                mActivityRef.get(), DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        Log.d(Const.TAG, "apk initData");
        getSupportLoaderManager().initLoader(Const.TASK_APK_ID, null, new ApkLoaderCallback());
    }

    class ApkLoaderCallback implements LoaderManager.LoaderCallbacks<List<ApkEntity>> {

        @Override
        public Loader<List<ApkEntity>> onCreateLoader(int id, Bundle args) {
            Toast.makeText(mActivityRef.get(), "apk onCreateLoader at " + SystemClock.currentThreadTimeMillis(), Toast.LENGTH_SHORT).show();
            Log.d(Const.TAG, "apk onCreateLoader at " + SystemClock.currentThreadTimeMillis());

            // Loader user app context in it's own
            return new ApkListLoader(ApkListActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<ApkEntity>> loader, List<ApkEntity> data) {
            Toast.makeText(mActivityRef.get(), "apk onLoadFinished", Toast.LENGTH_SHORT).show();
            Log.d(Const.TAG, "apk onLoadFinished");
            mApkAdapter.setData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<ApkEntity>> loader) {
            mApkAdapter.clearData();
            Log.d(Const.TAG, "apk onLoaderReset");
        }
    }

}
