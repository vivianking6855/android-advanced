package com.clean.photo.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.clean.R;
import com.clean.photo.adapter.PhotoCursorAdapter;
import com.clean.photo.listenter.IPhotoDisplayer;
import com.clean.photo.presenter.PhotoPresenter;
import com.open.appbase.fragment.BaseMVPLazyFragment;
import com.orhanobut.logger.Logger;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.clean.businesscommon.common.Const.PHOTO_GRID_NUM;
import static com.clean.businesscommon.common.Const.TAG_APP;

/**
 * Apk List Fragment
 */
public class PhotoListFragment extends BaseMVPLazyFragment<IPhotoDisplayer, PhotoPresenter> implements IPhotoDisplayer {
    private Reference<FragmentActivity> mActivityRef;

    private Unbinder unbinder;
    private PhotoCursorAdapter mAdapter;

    @BindView(android.R.id.list)
    RecyclerView recyclerView;
    @BindView(android.R.id.title)
    TextView statusTV;

    public static PhotoListFragment newInstance() {
        return new PhotoListFragment();
    }

    @Override
    protected PhotoPresenter createPresenter() {
        return new PhotoPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_apk_list;
    }

    @Override
    protected void initViews(View view) {
        if (getActivity() == null) {
            return;
        }

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new GridLayoutManager(mActivityRef.get(), PHOTO_GRID_NUM));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                mActivityRef.get(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PhotoCursorAdapter(mActivityRef.get());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mActivityRef = new WeakReference<>(getActivity());
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

        //mAdapter.releasePicasso();
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
