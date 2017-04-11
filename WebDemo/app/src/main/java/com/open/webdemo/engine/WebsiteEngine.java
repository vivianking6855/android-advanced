package com.open.webdemo.engine;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.open.webdemo.entity.SearchEntity;
import com.open.webdemo.entity.UrlConfig;
import com.open.webdemo.utils.Const;

import java.io.IOException;
import java.util.ArrayList;

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
    public final Gson mGson = new Gson();


    public static WebsiteEngine getInstance() {
        return ourInstance;
    }

    private WebsiteEngine() {
    }

    public static Gson getGson() {
        return getInstance().mGson;
    }

    /**
     * get config sync through okhttp
     */
    public UrlConfig getConfig() {
        try {
            Request request = new Request.Builder()
                    .url(Const.URL_CONFIG)
                    .build();

            Response response = mOkHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.w(TAG, "getConfig failed " + response);
                return null;
            }

            UrlConfig config = mGson.fromJson(response.body().charStream(), UrlConfig.class);
            return config;
        } catch (Exception ex) {
            Log.w(TAG, "getConfig ex:", ex);
        }

        return null;
    }

    /**
     * search sync through okhttp
     * filter :
     * "/1.1/threadview/search?key=" + key + "&start=" + startPosition + "&limit=" + SEARCH_LIMIT + "&sortby=dateline&order=desc";
     */
    public ArrayList<SearchEntity> search(String key) {
        try {
            final String startpos = "&start=0";
            final String url = Const.URL_SEARCH + key + startpos + Const.URL_SEARCH_END;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = mOkHttpClient.newCall(request).execute();
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

            return list;
        } catch (Exception ex) {
            Log.w(TAG, "search ex:", ex);
        }

        return null;
    }


}
