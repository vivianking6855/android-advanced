package com.vv.appuniform.presenter;

import com.vv.appuniform.share.base.BasePresenter;
import com.vv.appuniform.share.listenter.IHomeDisplayer;
import com.vv.appuniform.share.transaction.DataTransaction;
import com.wenxi.learn.data.repository.UserProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Home presenter.
 */
public class HomePresenter extends BasePresenter<IHomeDisplayer> {
    // rx
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * Fetch user.
     */
    public void fetchUser() {
        // load data
        Disposable data = Observable.fromCallable(UserProvider::getUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            mOuterWeakRef.get().onDisplay(DataTransaction.transform(user));
                        },
                        error -> {
                            // TODO
                        });

        compositeDisposable.add(data);
    }

    @Override
    public void detachReference() {
        super.detachReference();

        // release rx
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
