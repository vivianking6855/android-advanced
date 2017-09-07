package com.open.templatebasic.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.open.templatebasic.R;
import com.open.templatebasic.adapter.HomePagerAdapter;
import com.open.templatebasic.fragment.FristFragment;
import com.open.templatebasic.fragment.SecondFragment;
import com.open.templatebasic.utils.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {
    private final static String TAG = "MainActivity";

    // fragment
    private FristFragment firstFragment;
    private SecondFragment secondFragment;
    private int mCurrentId; // current page id

    // adapter
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        firstFragment = FristFragment.newInstance("param1", "param2");
        secondFragment = SecondFragment.newInstance("param1", "param2");
        mCurrentId = R.id.navigation_fist;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        // bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        initViewPager();
    }

    private void initViewPager() {
        HomePagerAdapter mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), this);
        mPagerAdapter.addFragment(firstFragment);
        mPagerAdapter.addFragment(secondFragment);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // disable viewpage scroll
//        mViewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
    }

    @Override
    protected void loadData() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // current id pretreatment
            int current = item.getItemId();
            if (current == mCurrentId) {
                Log.d(TAG, "onNavigationItemSelected not changed");
                return false;
            }
            mCurrentId = current;

            switch (current) {
                case R.id.navigation_fist:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_second:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_third:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_fourth:
                    mViewPager.setCurrentItem(1);
                    return true;
            }

            return false;
        }
    };


}
