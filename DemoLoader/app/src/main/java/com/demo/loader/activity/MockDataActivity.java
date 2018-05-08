package com.demo.loader.activity;

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
import com.demo.loader.common.Const;
import com.demo.loader.mockdata.DataListAdapter;
import com.demo.loader.mockdata.DataLoader;
import com.demo.loader.mockdata.MockEntity;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

public class MockDataActivity extends AppCompatActivity {
    private Reference<MockDataActivity> mActivityRef;

    private DataListAdapter mAdapter;

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
        recyclerView.setAdapter(mAdapter = new DataListAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                mActivityRef.get(), DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        Log.d(Const.TAG, "data initData");
        getSupportLoaderManager().initLoader(Const.TASK_DATA_ID, null, new DataLoaderCallback());
    }


    class DataLoaderCallback implements LoaderManager.LoaderCallbacks<List<MockEntity>> {

        @Override
        public Loader<List<MockEntity>> onCreateLoader(int id, Bundle args) {
            Toast.makeText(mActivityRef.get(), "loading", Toast.LENGTH_SHORT).show();
            // Loader user app context in it's own
            return new DataLoader(MockDataActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<MockEntity>> loader, List<MockEntity> data) {
            Toast.makeText(mActivityRef.get(), "finish", Toast.LENGTH_SHORT).show();
            mAdapter.setData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<MockEntity>> loader) {
            mAdapter.clearData();
        }
    }
}
