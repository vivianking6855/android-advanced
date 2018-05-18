package com.clean.user.presenter;

import android.support.v4.app.Fragment;

import com.clean.businesscommon.transaction.DataTransaction;
import com.clean.exception.ErrorMessageFactory;
import com.clean.user.listenter.IUserDisplayer;
import com.clean.user.model.UserModel;
import com.learn.data.common.ResultConst;
import com.learn.data.entity.UserEntity;
import com.learn.data.exception.DataException;
import com.learn.data.listener.IUserListener;
import com.learn.data.repository.UserRepo;
import com.learn.data.task.JobTask;
import com.open.appbase.presenter.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Home presenter.
 */
public class UserPresenter extends BasePresenter<IUserDisplayer> {
    // rx
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void fetchUserWithJobTask() {
        JobTask.THREAD_POOL_EXECUTOR.execute(() -> {
            try {
                UserEntity data = UserRepo.getUser();
                (((Fragment) viewWeakRef.get()).getActivity()).runOnUiThread(() -> {
                    viewWeakRef.get().onDisplay(DataTransaction.transformUser(data));
                });
            } catch (DataException e) {
                (((Fragment) viewWeakRef.get()).getActivity()).runOnUiThread(() -> {
                    String error = ErrorMessageFactory.create((((Fragment) viewWeakRef.get()).getActivity()), e);
                    viewWeakRef.get().onDisplay(new UserModel(null, error));
                });
            }
        });
    }

    public void fetchUserWithListener() {
        UserRepo.getUserAsync(new IUserListener() {
                                  @Override
                                  public void onSuccess(UserEntity user) {
                                      (((Fragment) viewWeakRef.get()).getActivity()).runOnUiThread(() -> {
                                          viewWeakRef.get().onDisplay(DataTransaction.transformUser(user));
                                      });
                                  }

                                  @Override
                                  public void onError(Exception e) {
                                      (((Fragment) viewWeakRef.get()).getActivity()).runOnUiThread(() -> {
                                          String error = ErrorMessageFactory.create((((Fragment) viewWeakRef.get()).getActivity()), e);
                                          viewWeakRef.get().onDisplay(new UserModel(null, error));
                                      });
                                  }
                              }
        );
    }

    /**
     * Fetch user.
     */
    public void fetchUserWithRx() {
        // load data
        Disposable data = Observable.fromCallable(UserRepo::getUserResult)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.state == ResultConst.DATA_SUCCESS) {
                        viewWeakRef.get().onDisplay(DataTransaction.transformUser(result.infos));
                    } else {
                        String error = ErrorMessageFactory.createWithErrorCode((((Fragment) viewWeakRef.get()).getActivity()),
                                result.state);
                        viewWeakRef.get().onDisplay(new UserModel(null, error));
                    }
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
