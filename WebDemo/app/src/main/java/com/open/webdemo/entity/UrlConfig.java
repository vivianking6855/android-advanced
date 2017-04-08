package com.open.webdemo.entity;

import java.util.List;

/**
 * Created by vivian on 2017/4/8.
 */

public class UrlConfig {

    public List<DebugBean> Debug;
    public List<DefaultBean> Default;
    public List<LocalBean> Local;

    public static class DebugBean {
        /**
         * tag : host
         * url : http://zentalk.asus.com.cn:8888
         */

        public String tag;
        public String url;
    }

    public static class DefaultBean {
        /**
         * tag : host
         * url : http://zentalk.asus.com.cn:8888
         */

        public String tag;
        public String url;
    }

    public static class LocalBean {
        /**
         * tag : home
         * url : http://172.29.8.186:8080/zentalk/H5/recommend.html
         */

        public String tag;
        public String url;
    }
}
