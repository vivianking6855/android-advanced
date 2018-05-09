package com.learn.data.listener;

import com.learn.data.entity.UserEntity;

public interface IUserListener {
    void onSuccess(UserEntity user);

    void onError();
}
