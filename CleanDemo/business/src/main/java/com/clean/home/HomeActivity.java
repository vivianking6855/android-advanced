package com.clean.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.clean.R;
import com.clean.businesscommon.base.BaseActivity;
import com.clean.home.adapter.RecyclerViewArrayAdapter;

import java.lang.ref.Reference;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeActivity
 * IApkDisplayer as V in MVP
 */
public class HomeActivity extends BaseActivity {
    private Reference<HomeActivity> mActivityRef;

    @BindView(android.R.id.list)
    RecyclerView recyclerView;
    @BindView(android.R.id.title)
    TextView resultTV;

    @BindArray(R.array.home)
    String[] homeList;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                HomeActivity.this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void loadData() {
        recyclerView.setAdapter(new RecyclerViewArrayAdapter(this, homeList));

    }

    /**
     * Gets calling intent.
     *
     * @param context the context
     * @return the calling intent
     */
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

}
