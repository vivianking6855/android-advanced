package com.clean.apklist.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.clean.apklist.adapter.ApkListAdapter;
import com.clean.apklist.listenter.IApkDisplayer;
import com.learn.data.entity.ApkEntity;
import com.clean.businesscommon.common.Const;
import com.open.appbase.presenter.BasePresenter;

import java.util.List;


/**
 * The type Home presenter.
 */
public class ApkPresenter extends BasePresenter<IApkDisplayer> {

    private ApkListAdapter mAdapter;

    public void setAdapter(ApkListAdapter adapter) {
        mAdapter = adapter;
    }

    public void startLoader() {
        if (viewWeakRef.get() == null) {
            return;
        }
        (((Fragment) viewWeakRef.get()).getActivity()).
                getSupportLoaderManager().initLoader(Const.TASK_HOME_ID, null, new HomeLoaderCallback());
    }

    class HomeLoaderCallback implements LoaderManager.LoaderCallbacks<List<ApkEntity>> {
        @NonNull
        @Override
        public Loader<List<ApkEntity>> onCreateLoader(int id, @Nullable Bundle args) {
            if (viewWeakRef.get() == null) {
                return null;
            }
            viewWeakRef.get().onDisplay("start loading");


            // Loader use application context in it's own
            return new ApkListLoader(((Fragment) viewWeakRef.get()).getActivity());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<ApkEntity>> loader, List<ApkEntity> data) {
            if (mAdapter == null || data == null) {
                return;
            }
            mAdapter.setData(data);
            viewWeakRef.get().onDisplay("load finish!");
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
