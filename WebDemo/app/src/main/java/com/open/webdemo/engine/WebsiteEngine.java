package com.open.webdemo.engine;

import android.util.Log;

import com.google.gson.Gson;
import com.open.webdemo.entity.UrlConfig;
import com.open.webdemo.entity.UrlConfigEvent;
import com.open.webdemo.utils.Const;
import com.open.webdemo.utils.WebUtil;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by vivian on 2017/4/8.
 */

public class WebsiteEngine {
    private static final String TAG = WebsiteEngine.class.getSimpleName();

    private static final WebsiteEngine ourInstance = new WebsiteEngine();

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private final Gson mGson = new Gson();

    public static WebsiteEngine getInstance() {
        return ourInstance;
    }

    private WebsiteEngine() {
    }

    public void getConfig() {
        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response res = WebUtil.getOkHttp(Const.URL_CONFIG, mOkHttpClient);
                    UrlConfig config = mGson.fromJson(res.body().charStream(), UrlConfig.class);
                    EventBus.getDefault().post(new UrlConfigEvent(config));
                } catch (Exception ex) {
                    Log.w(TAG, "getConfig ex", ex);
                }
            }
        });
        test.start();
    }

    public String search() {
        return "";
    }
}
