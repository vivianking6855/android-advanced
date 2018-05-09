package com.clean.apklist.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.clean.apklist.adapter.ApkListAdapter;
import com.clean.apklist.listenter.IApkDisplayer;
import com.learn.data.entity.ApkEntity;
import com.clean.businesscommon.base.BasePresenter;
import com.clean.businesscommon.common.Const;

import java.util.List;


/**
 * The type Home presenter.
 */
public class ApkPresenter extends BasePresenter<IApkDisplayer> {

    private ApkListAdapter mAdapter;

    public ApkPresenter(ApkListAdapter adapter) {
        mAdapter = adapter;
    }

    public void startLoader() {
        (((Fragment) mOuterWeakRef.get()).getActivity()).
                getSupportLoaderManager().initLoader(Const.TASK_HOME_ID, null, new HomeLoaderCallback());
    }

    class HomeLoaderCallback implements LoaderManager.LoaderCallbacks<List<ApkEntity>> {
        @NonNull
        @Override
        public Loader<List<ApkEntity>> onCreateLoader(int id, @Nullable Bundle args) {
            mOuterWeakRef.get().onDisplay("start loading");

            // Loader use application context in it's own
            return new ApkListLoader((Context) mOuterWeakRef.get());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<ApkEntity>> loader, List<ApkEntity> data) {
            mAdapter.setData(data);
            mOuterWeakRef.get().onDisplay("load finish!");
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<ApkEntity>> loader) {
            mAdapter.clearData();
        }
    }

    @Override
    public void detachReference() {
        super.detachReference();
    }

}
