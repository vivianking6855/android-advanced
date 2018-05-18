package com.clean.photo.presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.clean.apklist.adapter.ApkListAdapter;
import com.clean.businesscommon.common.Const;
import com.clean.photo.adapter.PhotoCursorAdapter;
import com.clean.photo.listenter.IPhotoDisplayer;
import com.learn.data.repository.PhotoRepo;
import com.open.appbase.presenter.BasePresenter;
import com.orhanobut.logger.Logger;


/**
 * The type Home presenter.
 */
public class PhotoPresenter extends BasePresenter<IPhotoDisplayer> {

    private PhotoCursorAdapter mPhotoAdapter;

    public void setAdapter(PhotoCursorAdapter adapter) {
        mPhotoAdapter = adapter;
    }

    public void startLoader() {
        // init Loader，LoaderManager will call onCreateLoader to get Loader and callback onLoadFinished when finished
        (((Fragment) viewWeakRef.get()).getActivity()).
                getSupportLoaderManager().initLoader(Const.TASK_PHOTO_ID, null, new PhotoLoaderCallback());
    }

    class PhotoLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            viewWeakRef.get().onDisplay("photo loading");
            return PhotoRepo.getCursorLoader((((Fragment) viewWeakRef.get()).getActivity()));
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (mPhotoAdapter == null || viewWeakRef.get() == null) {
                return;
            }

            if (data == null || data.getCount() == 0) {
                Logger.w("empty photo data");
                return;
            } else {
                mPhotoAdapter.swapCursor(data);
            }

            viewWeakRef.get().onDisplay("photo load finish! " + data.getCount());
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
