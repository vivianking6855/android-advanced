package com.clean.apklist.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.learn.data.entity.ApkEntity;
import com.clean.businesscommon.common.Const;
import com.learn.data.repository.ApkRepo;

import java.util.List;

public class ApkListLoader extends AsyncTaskLoader<List<ApkEntity>> {

    ApkListLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<ApkEntity> loadInBackground() {
        Log.d(Const.LOG_TAG, "apk loadInBackground");
        return ApkRepo.getLocalAppList(getContext().getPackageManager());
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        stopLoading();
    }
}
