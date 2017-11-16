package com.vv.cache.presenter;

import android.content.Context;
import android.util.Log;

import com.open.utislib.cache.DiskLruCacheUtils;
import com.vv.cache.cache.GlobalManager;
import com.vv.cache.cache.LruCacheManager;
import com.vv.cache.listener.IDownloadListener;
import com.vv.cache.model.DataApi;
import com.vv.cache.model.DataModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vivian on 2017/11/13.
 */

public class DownloadPresenter {
    private static final String TAG = "DownloadPresenter";
    private IDownloadListener mListener;
    // rx
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Context mContext;

    public DownloadPresenter(Context context) {
        mContext = context;
    }

    public void setListener(IDownloadListener listener) {
        mListener = listener;
    }

    public void loadData() {
        Log.d(TAG, "loadData");
        // load start notify
        if (mListener != null) {
            mListener.OnLoadStart();
        }
        // load data
        Disposable dispose = Observable.fromCallable(DataApi::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            //Log.d(TAG, "loadData Success: " + StringUtils.join(data.toArray(), ","));
                            if (mListener != null) {
                                mListener.OnLoadSuccess(data);
                            }

                            // add to disk cache
                            cacheToDisk(data);
                            // add to lru cache
                            cacheToLru(data);

                            GlobalManager.INSTANCE.mDataCount = data.size();
                        },
                        error -> {
                            Log.w(TAG, "loadData error: ", error);
                            if (mListener != null) {
                                mListener.OnLoadFail(error.toString());
                            }
                        });

        compositeDisposable.add(dispose);
    }

    private void cacheToDisk(List<DataModel> list) {
        for (DataModel data : list) {
            DiskLruCacheUtils.set(data.id, data.description);
        }
    }

    private void cacheToLru(List<DataModel> list) {
        for (DataModel data : list) {
            LruCacheManager.INSTANCE.getLruCache(mContext).put(data.id, data.description);
        }
    }

    public String getFromDiskCache(String key) {
        return DiskLruCacheUtils.getString(key);
    }

    public String getFromLruCache(String key) {
        return LruCacheManager.INSTANCE.getLruCache(mContext).get(key);
    }

    public String getFromCache(String key) {
        String str = getFromLruCache(key);
        if (str != null) {
            Log.d(TAG,"getFromCache is from lru cache");
            return str;
        }
        Log.d(TAG,"getFromCache is from disk lru cache");
        return getFromDiskCache(key);
    }

    public void destroy() {
        // to avoid okhttp leak
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
