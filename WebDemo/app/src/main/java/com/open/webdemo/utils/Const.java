package com.open.webdemo.utils;

/**
 * Created by vivian on 2017/4/8.
 */

final public class Const {
    // host
    public final static String URL_HOST = "http://zentalk.asus.com.cn:8888";
    // url config
    public final static String URL_CONFIG = URL_HOST + "/1.0/urlconfig";
    // search url
    public final static int SEARCH_LIMIT = 5;
    public final static String URL_SEARCH = URL_HOST + "/1.1/threadview/search?key=";
    public final static String URL_SEARCH_END = "&limit=" + SEARCH_LIMIT + "&sortby=dateline&order=desc";
}
