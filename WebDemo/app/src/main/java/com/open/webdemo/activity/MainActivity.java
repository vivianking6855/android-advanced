package com.open.webdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.open.webdemo.R;
import com.open.webdemo.engine.WebsiteEngine;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTVShow;

    // website
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTVShow = (TextView) findViewById(R.id.tv_show);
    }

    public void okhttpConfigClick(View view) {
        Disposable config = Observable.fromCallable(() -> WebsiteEngine.getInstance().getConfig())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(urlConfig -> {
                            final String result = WebsiteEngine.getGson().toJson(urlConfig.Debug).toString();
                            Log.d(TAG, "" + result);
                            mTVShow.setText(result);
                        },
                        error -> Log.w(TAG, "okhttpConfigClick ex:", error),
                        () -> Log.d(TAG, "okhttpConfigClick complete"));
        compositeDisposable.add(config);
    }

    public void okhttpSearchClick(View view) {
        final String search_key = "zenfone3";
        Disposable search = Single.fromCallable(() -> WebsiteEngine.getInstance().search(search_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            final String result = WebsiteEngine.getGson().toJson(list).toString();
                            Log.d(TAG, "" + result);
                            mTVShow.setText(result);
                        },
                        error -> Log.w(TAG, "okhttpSearchClick ex:", error));
        compositeDisposable.add(search);
    }

    public void retrofitConfigClick(View view) {
        Disposable disposable = Single.fromCallable(() -> WebsiteEngine.getInstance().getConfigThroughRetrofit())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                            final String result = "" + WebsiteEngine.getGson().toJson(item).toString();
                            Log.d(TAG, result);
                            mTVShow.setText(result);
                        },
                        error -> Log.w(TAG, "retrofitConfigClick ex:", error));
        compositeDisposable.add(disposable);
    }

    public void retrofitSearchClick(View view) {
        final String search_key = "zenfone3";
        Disposable disposable = Single.fromCallable(() -> WebsiteEngine.getInstance().searchThroughRetrofit(search_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                            final String result = WebsiteEngine.getGson().toJson(item).toString();
                            Log.d(TAG, "" + result);
                            mTVShow.setText(result);
                        },
                        error -> Log.w(TAG, "retrofitSearchClick ex:", error));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
