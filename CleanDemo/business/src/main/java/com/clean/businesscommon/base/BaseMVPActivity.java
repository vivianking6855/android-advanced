package com.clean.businesscommon.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * The type Base mvp activity.
 * you need implement createPresenter to create Presenter
 * which must extends {@link BasePresenter}
 * or you can Override onCreate to customized your attachReference
 *
 * @param <V> the type parameter
 * @param <T> the type parameter
 */
public abstract class BaseMVPActivity<V, T extends BasePresenter<V>> extends BaseActivity {
    /**
     * The presenter P in MPV
     */
    protected T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // create presenter
        mPresenter = createPresenter();
        mPresenter.attachReference((V) this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachReference();
    }

    /**
     * Create presenter t that extends {@link BasePresenter}
     *
     * @return the t
     */
    protected abstract T createPresenter();
}
