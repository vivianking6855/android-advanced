package com.open.templatebasic.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by vivian on 2017/8/31.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private final static String TAG = "HomePagerAdapter";
    private Context mContext;
    private ArrayList<Fragment> mList;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        mContext = context;
        mList = new ArrayList<Fragment>();
    }

    public void addFragment(Fragment fragment){
        mList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
