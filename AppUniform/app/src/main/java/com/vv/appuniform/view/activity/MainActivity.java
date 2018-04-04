package com.vv.appuniform.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.vv.appuniform.R;
import com.vv.appuniform.model.UserModel;
import com.vv.appuniform.presenter.HomePresenter;
import com.vv.appuniform.share.listenter.IHomeDisplayer;
import com.vv.appuniform.share.base.BaseMVPActivity;

/**
 * The type Main activity.
 * IHomeDisplayer as V in MVP
 */
public class MainActivity extends BaseMVPActivity<IHomeDisplayer, HomePresenter> implements IHomeDisplayer {
    private TextView mResult;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void loadData() {
        mPresenter.fetchUser();
    }

    /**
     * Gets calling intent.
     *
     * @param context the context
     * @return the calling intent
     */
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onDisplay(UserModel model) {
        if (model != null) {
            mResult = (TextView) findViewById(R.id.result);
            mResult.setText(model.getUserId());
        }
    }
}
