package com.open.webdemo.engine;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.open.webdemo.entity.SearchEntity;
import com.open.webdemo.entity.SearchEvent;
import com.open.webdemo.entity.UrlConfig;
import com.open.webdemo.entity.UrlConfigEvent;
import com.open.webdemo.utils.Const;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    /**
     * call okhttp sync method
     */
    public void getConfig() {
        Thread configThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(Const.URL_CONFIG)
                            .build();

                    Response response = mOkHttpClient.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        Log.w(TAG, "getConfig failed " + response);
                        return;
                    }
                    UrlConfig config = mGson.fromJson(response.body().charStream(), UrlConfig.class);
                    EventBus.getDefault().post(new UrlConfigEvent(config));
                } catch (Exception ex) {
                    Log.w(TAG, "getConfig ex:", ex);
                }
            }
        });
        configThread.start();
    }

    /**
     * call okhttp async
     * filter :
     * "/1.1/threadview/search?key=" + key + "&start=" + startPosition + "&limit=" + SEARCH_LIMIT + "&sortby=dateline&order=desc";
     */
    public void search() {
        final String search_key = "zenfone3";
        final String startpos = "&start=0";
        final String url = Const.URL_SEARCH + search_key + startpos + Const.URL_SEARCH_END;

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.w(TAG, "search ex:", new IOException("Unexpected code " + response));
                }
                // parse array
                JsonParser parser = new JsonParser();
                JsonArray array = parser.parse(response.body().string()).getAsJsonArray();
                ArrayList<SearchEntity> list = new ArrayList<SearchEntity>();
                if (array != null) {
                    for (JsonElement ele : array) {
                        SearchEntity entity = mGson.fromJson(ele, SearchEntity.class);
                        list.add(entity);
                    }
                }
                EventBus.getDefault().post(new SearchEvent(list));
            }
        });
    }
}
