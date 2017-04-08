package com.open.webdemo.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vivian on 2017/4/8.
 */

public class WebUtil {

    /**
     * Synchronous Get from website through Okhttp
     * get strings or object:
     * 1. response.body().string() to get strings
     * 2. gson.fromJson(response.body().charStream(), Test.class)
     * get headers : response.headers();
     *
     * @param url
     * @param client OkHttpClient
     * @return Response
     * @throws Exception
     */
    public static Response getOkHttp(String url, OkHttpClient client) throws Exception {
        Request request = new Request.Builder()
                .url(url)//"http://publicobject.com/helloworld.txt"
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        return response;
    }
}
