package com.open.webdemo.utils;

/**
 * Created by vivian on 2017/4/8.
 */

final public class Const {
    // host
    public final static String URL_HOST = "http://zentalk.asus.com.cn:8888";
    // url config
    public final static String URL_CONFIG_BASE = "http://zentalk.asus.com.cn:8888/1.0/";
    public final static String URL_CONFIG_EXTENDS = "urlconfig";
    public final static String URL_CONFIG = URL_CONFIG_BASE + URL_CONFIG_EXTENDS;
    // search url, params: search?key={key}&start={start}&limit={limit}&sortby=dateline&order=desc
    public final static String URL_SEARCH_BASE = URL_HOST + "/1.1/threadview/";
}
