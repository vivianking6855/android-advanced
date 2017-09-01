package com.open.dynamicfragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by vivian on 2017/8/31.
 */

public class BookPagerAdapter extends FragmentPagerAdapter {
    private final static String TAG = "BookPagerAdapter";
    private Context mContext;
    private String[] mCourseTitles;
    private String[] mCourseDescriptions;
    private String[] mCourseTitlesShort;

    public BookPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        mContext = context;
        mCourseTitles = mContext.getResources().getStringArray(R.array.bookTitles);
        mCourseDescriptions = mContext.getResources().getStringArray(R.array.bookDescriptions);
        mCourseTitlesShort = mContext.getResources().getStringArray(R.array.bookTitles);
    }

    @Override
    public Fragment getItem(int position) {
        // Create the fragment instance and pass the arguments
        BookFragment bookFragment = BookFragment.newInstance(mCourseTitles[position],
                mCourseDescriptions[position]);
        // return the fragment instance
        return bookFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCourseTitlesShort[position];
    }

    @Override
    public int getCount() {
        return mCourseTitlesShort.length;
    }
}
