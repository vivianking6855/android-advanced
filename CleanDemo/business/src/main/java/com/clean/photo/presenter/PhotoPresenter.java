package com.clean.photo.presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.clean.apklist.adapter.ApkListAdapter;
import com.clean.apklist.listenter.IApkDisplayer;
import com.clean.apklist.presenter.ApkListLoader;
import com.clean.businesscommon.base.BasePresenter;
import com.clean.businesscommon.common.Const;
import com.clean.photo.adapter.PhotoCursorAdapter;
import com.clean.photo.listenter.IPhotoDisplayer;
import com.learn.data.entity.ApkEntity;
import com.learn.data.repository.PhotoRepo;

import java.util.List;


/**
 * The type Home presenter.
 */
public class PhotoPresenter extends BasePresenter<IPhotoDisplayer> {

    private PhotoCursorAdapter mPhotoAdapter;

    public PhotoPresenter(PhotoCursorAdapter adapter) {
        mPhotoAdapter = adapter;
    }

    public void startLoader() {
        // init Loader，LoaderManager will call onCreateLoader to get Loader and callback onLoadFinished when finished
        (((Fragment) mOuterWeakRef.get()).getActivity()).
                getSupportLoaderManager().initLoader(Const.TASK_PHOTO_ID, null, new PhotoLoaderCallback());
    }

    class PhotoLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            mOuterWeakRef.get().onDisplay("photo loading");
            return PhotoRepo.getCursorLoader((Context) mOuterWeakRef.get());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mOuterWeakRef.get().onDisplay("photo load finish!");
            if (data == null || data.getCount() == 0) {
                Log.w(Const.LOG_TAG, "photo onLoadFinished empty data");
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

    @Override
    public void detachReference() {
        super.detachReference();
    }

}
