package com.clean.apklist.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.clean.apklist.adapter.ApkListAdapter;
import com.clean.apklist.listenter.IApkDisplayer;
import com.clean.apklist.presenter.ApkPresenter;
import com.open.appbase.fragment.BaseMVPLazyFragment;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Apk List Fragment
 */
public class ApkListFragment extends BaseMVPLazyFragment<IApkDisplayer, ApkPresenter> implements IApkDisplayer {
    private Reference<FragmentActivity> mActivityRef;

    private Unbinder unbinder;
    private ApkListAdapter mAdapter;

    @BindView(android.R.id.list)
    RecyclerView recyclerView;
    @BindView(android.R.id.title)
    TextView statusTV;

    public static ApkListFragment newInstance() {
        return new ApkListFragment();
    }

    @Override
    protected ApkPresenter createPresenter() {
        return new ApkPresenter(mAdapter);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initViews(View view) {
        if (getActivity() == null) {
            return;
        }

        mActivityRef = new WeakReference<>(getActivity());

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivityRef.get()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        mAdapter = new ApkListAdapter(mActivityRef.get());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.startLoader();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDisplay(String msg) {
        statusTV.setText(msg);
    }
}
