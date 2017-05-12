package com.open.webdemo.interfaces;

import com.open.webdemo.home.entity.SearchEntity;
import com.open.webdemo.home.entity.UrlConfig;
import com.open.webdemo.utils.Const;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vivian on 2017/4/12.
 */

public interface IWebSiteService {
    @GET("search")
    Call<ResponseBody> search(@Query("key") String key,
                              @Query("start") int start,
                              @Query("limit") int limit,
                              @Query("sortby") String sortby, @Query("order") String order);

    @GET(Const.URL_CONFIG_EXTENDS)
    Call<UrlConfig> getUrlConfig();
}
