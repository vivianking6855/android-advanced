package com.demo.loader.activity;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.demo.loader.R;
import com.demo.loader.common.Const;
import com.demo.loader.photo.PhotoCursorAdapter;
import com.demo.loader.photo.PhotoDataHelper;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class PhotoActivity extends AppCompatActivity {
    private Reference<PhotoActivity> mActivityRef;

    private RecyclerView mPhotoRecyclerView;
    private PhotoCursorAdapter mPhotoAdapter;
    private PhotoDataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_list);

        mActivityRef = new WeakReference<>(this);

        initData();
        initView();
        loadData();
    }

    private void initData() {
        mDataHelper = new PhotoDataHelper(mActivityRef.get());
    }

    private void initView() {
        mPhotoAdapter = new PhotoCursorAdapter(mActivityRef.get());
        RecyclerView recyclerView = findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(PhotoActivity.this));
        recyclerView.setAdapter(mPhotoAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                mActivityRef.get(), DividerItemDecoration.VERTICAL));
    }

    private void loadData(){
        // init Loader，LoaderManager will call onCreateLoader to get Loader and callback onLoadFinished when finished
        getSupportLoaderManager().initLoader(Const.TASK_PHOTO_ID, null, new PhotoLoaderCallback());
    }

    class PhotoLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.d(Const.TAG, "photo onCreateLoader");
            return mDataHelper.getCursorLoader();
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(Const.TAG, "photo onLoadFinished");
            if (data == null || data.getCount() == 0) {
                Log.w(Const.TAG, "photo onLoadFinished empty data");
                return;
            } else {
                mPhotoAdapter.swapCursor(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mPhotoAdapter.swapCursor(null);
        }
    }
}
