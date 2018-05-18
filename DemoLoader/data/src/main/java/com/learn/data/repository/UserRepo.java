package com.learn.data.repository;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.learn.data.entity.ResultEntity;
import com.learn.data.entity.UserEntity;
import com.learn.data.exception.DataException;
import com.learn.data.listener.IUserListener;
import com.learn.data.task.JobTask;

import static com.learn.data.common.ResultConst.DATA_ERROR;
import static com.learn.data.common.ResultConst.DATA_NO_USER;
import static com.learn.data.common.ResultConst.DATA_SUCCESS;

/**
 * User repo, all user data will be fetched here
 */
public final class UserRepo {
    /**
     * Gets user async with IUserListener.
     *
     * @param listener the listener
     */
    public static void getUserAsync(@NonNull final IUserListener listener) {
        JobTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                // consume task
                SystemClock.sleep(2000);

                //String mockId = "mockId";
                String mockId = null;
                if (mockId == null || mockId.isEmpty()) {
                    listener.onError(new DataException(DATA_NO_USER));
                } else {
                    listener.onSuccess(new UserEntity(mockId));
                }
            }
        });
    }

    /**
     * Gets user sync, may throws DataException when user is null
     *
     * @return the user
     * @throws DataException the data exception
     */
    public static UserEntity getUser() throws DataException {
        // consume task
        SystemClock.sleep(2000);

        String mockId = "mockId";
        //String mockId = null;
        if (mockId == null || mockId.isEmpty()) {
            throw new DataException(DATA_NO_USER);
        }
        return new UserEntity(mockId);
    }

    /**
     * Gets user result with ResultEntity
     *
     * @return the user result
     */
    public static ResultEntity<UserEntity> getUserResult() {
        ResultEntity<UserEntity> result = new ResultEntity<>();
        result.state = DATA_SUCCESS;

        // consume task
        SystemClock.sleep(2000);

        String mockId = "mockId";
        //String mockId = null;
        if (mockId == null || mockId.isEmpty()) {
            result.state = DATA_NO_USER;
        } else {
            result.infos = new UserEntity(mockId);
        }

        return result;
    }


}
