package com.open.webdemo.entity;

import java.util.ArrayList;

/**
 * Created by vivian on 2017/4/8.
 */

public class SearchEvent {
    public ArrayList<SearchEntity> mEntityList;

    public SearchEvent(ArrayList<SearchEntity> list) {
        mEntityList = (ArrayList) list.clone();
    }
}
