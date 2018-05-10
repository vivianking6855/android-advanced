package com.clean.photo.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.clean.photo.adapter.PhotoCursorAdapter;
import com.clean.photo.listenter.IPhotoDisplayer;
import com.clean.photo.presenter.PhotoPresenter;
import com.open.appbase.fragment.BaseMVPLazyFragment;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.clean.businesscommon.common.Const.PHOTO_GRID_NUM;

/**
 * Apk List Fragment
 */
public class PhotoListFragment extends BaseMVPLazyFragment<IPhotoDisplayer, PhotoPresenter> implements IPhotoDisplayer{
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
        return new PhotoPresenter(mAdapter);
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

        recyclerView.setLayoutManager(new GridLayoutManager(mActivityRef.get(), PHOTO_GRID_NUM));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                mActivityRef.get(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        mAdapter = new PhotoCursorAdapter(mActivityRef.get());
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

        // if you don't want to clear Picasso photo, comment it
        mAdapter.releasePicasso();
    }

    @Override
    public void onDisplay(String msg) {
        statusTV.setText(msg);
    }
}
