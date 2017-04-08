package com.open.webdemo.utils;

/**
 * Created by vivian on 2017/4/8.
 */

final public class Const {
    public final static String URL_CONFIG = "http://zentalk.asus.com.cn:8888/1.0/urlconfig";

    public final static String URL_HOST = "http://zentalk.asus.com.cn:8888";

    public final static int SEARCH_LIMIT = 40;
    public final static String URL_SEARCH = URL_HOST + "/1.1/threadview/search?key=";
    public final static String URL_SEARCH_MID = "&limit=" + SEARCH_LIMIT + "&sortby=dateline&order=desc";

    //+ URLEncoder.encode(SEARCH_KEY, "UTF-8") + "&start=" + startPosition + "&limit=" + SEARCH_LIMIT + "&sortby=dateline&order=desc";


}
