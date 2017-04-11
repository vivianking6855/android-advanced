package com.open.webdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.open.webdemo.R;
import com.open.webdemo.engine.WebsiteEngine;
import com.open.webdemo.entity.UrlConfig;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTVShow;

    private Disposable mConfigDisposable;

    // website
    private Observable<UrlConfig> mConfigObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTVShow = (TextView) findViewById(R.id.tv_show);
    }

    public void okhttpConfigClick(View view) {
        mConfigDisposable = (Observable.fromCallable(() -> WebsiteEngine.getInstance().getConfig()).subscribeOn(Schedulers.io()))
                .subscribe(urlConfig -> {
                            final String result = WebsiteEngine.getGson().toJson(urlConfig.Debug).toString();
                            Log.d(TAG, "" + result);
                        },
                        error -> Log.w(TAG, "okhttpConfigClick ex:", error),
                        () -> Log.d(TAG, "okhttpConfigClick complete"));
    }

    public void okhttpSearchClick(View view) {
        final String search_key = "zenfone3";
        Observable.fromCallable(() -> WebsiteEngine.getInstance().search(search_key)).subscribeOn(Schedulers.io())
                .subscribe(list -> {
                            final String result = WebsiteEngine.getGson().toJson(list).toString();
                            Log.d(TAG, "" + result);
                        },
                        error -> Log.w(TAG, "okhttpSearchClick ex:", error),
                        () -> Log.d(TAG, "okhttpSearchClick complete"));
    }

    public void retrofitClick(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mConfigDisposable != null && !mConfigDisposable.isDisposed()){
            mConfigDisposable.dispose();
        }
    }
}
