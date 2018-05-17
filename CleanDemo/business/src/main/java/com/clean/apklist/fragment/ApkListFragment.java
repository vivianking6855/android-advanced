package com.clean.apklist.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.clean.R;
import com.clean.apklist.adapter.ApkListAdapter;
import com.clean.apklist.listenter.IApkDisplayer;
import com.clean.apklist.presenter.ApkPresenter;
import com.open.appbase.fragment.BaseMVPLazyFragment;
import com.orhanobut.logger.Logger;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.clean.businesscommon.common.Const.TAG_APP;

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
        return new ApkPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_apk_list;
    }

    @Override
    protected void initData() {
        mActivityRef = new WeakReference<>(getActivity());
    }

    @Override
    protected void initViews(View view) {
        if (getActivity() == null) {
            return;
        }

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivityRef.get()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new ApkListAdapter(mActivityRef.get());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.setAdapter(mAdapter);
        mPresenter.startLoader();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        safeDestroy();
    }

    private void safeDestroy() {
        try {
            unbinder.unbind();
        } catch (Exception ex) {
            Logger.w("safeDestroy ex", ex);
        }
    }

    @Override
    public void onDisplay(String msg) {
        statusTV.setText(msg);
    }
}
