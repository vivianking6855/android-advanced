package com.clean.user.fragment;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.clean.R;
import com.clean.user.listenter.IUserDisplayer;
import com.clean.user.model.UserModel;
import com.clean.user.presenter.UserPresenter;
import com.open.appbase.fragment.BaseMVPLazyFragment;
import com.orhanobut.logger.Logger;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Apk List Fragment
 */
public class UserFragment extends BaseMVPLazyFragment<IUserDisplayer, UserPresenter> implements IUserDisplayer {
    private Reference<FragmentActivity> mActivityRef;

    private Unbinder unbinder;

    @BindView(android.R.id.title)
    TextView titleTV;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user;
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
    }

    @Override
    protected void loadData() {
        // mPresenter.fetchUserWithRx();
        //mPresenter.fetchUserWithJobTask();
        mPresenter.fetchUserWithListener();
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
    public void onDisplay(UserModel user) {
        if (user != null) {
            titleTV.setText(user.toString());
        }
    }
}
