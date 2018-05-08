package com.demo.loader.applist;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.demo.loader.common.Const;

import java.util.List;

public class ApkListLoader extends AsyncTaskLoader<List<ApkEntity>> {
    public ApkListLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<ApkEntity> loadInBackground() {
        Log.d(Const.TAG, "apk loadInBackground");
        SystemClock.sleep(1000*5);
        return ApkTool.getLocalAppList(getContext().getPackageManager());
    }

    @Override
    protected void onStartLoading() {
        Log.d(Const.TAG, "apk onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        Log.d(Const.TAG, "apk onStopLoading");
    }

    @Override
    protected void onReset() {
        stopLoading();
        Log.d(Const.TAG, "apk onReset");
    }
}
