package com.vv.performancedemo.model;

/**
 * Created by vivian on 2017/3/15.
 */

import com.vv.performancedemo.R;

import java.util.ArrayList;
import java.util.List;

public class Droid {
    public String name;
    public int imageId;
    public String date;
    public String msg;


    public Droid(String msg, String date, int imageId, String name) {
        this.msg = msg;
        this.date = date;
        this.imageId = imageId;
        this.name = name;
    }

    public static List<Droid> generateDatas() {
        List<Droid> datas = new ArrayList<Droid>();

        datas.add(new Droid("Go To ClipRectActivity", "3分钟前", -1, "alex"));
        datas.add(new Droid("Hierarchy Viewer Optimze", "12分钟前", R.drawable.cap, "john"));
        datas.add(new Droid("yeah, put your hand on it", "17分钟前", -1, "jame"));
        datas.add(new Droid("it's a nic day, right", "33分钟前", R.drawable.camera, "jack"));

        return datas;
    }
}
