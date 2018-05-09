package com.clean.user.presenter;

import com.clean.businesscommon.base.BasePresenter;
import com.learn.data.listener.IUserListener;
import com.learn.data.repository.UserRepo;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Home presenter.
 */
public class UserPresenter extends BasePresenter<IUserListener> {
    // rx
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * Fetch user.
     */
    public void fetchUser() {
        // load data
        Disposable data = Observable.fromCallable(UserRepo::getUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                           // mOuterWeakRef.get().onDisplay(DataTransaction.transform(user));
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
