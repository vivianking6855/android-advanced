package com.vv.cache.listener;

import com.vv.cache.model.DataModel;

import java.util.List;

/**
 * Created by vivian on 2017/9/12.
 * Listener for download data
 */

public interface IDownloadListener {
    void OnLoadStart();

    void OnLoadSuccess(List<DataModel> data);

    void OnLoadFail(String error);
}
