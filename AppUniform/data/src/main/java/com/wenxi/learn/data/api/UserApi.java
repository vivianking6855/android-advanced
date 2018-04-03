package com.wenxi.learn.data.api;

import com.wenxi.learn.data.entity.UserEntity;

public final class UserApi {
    public static UserEntity getUser() {
        return new UserEntity();
    }
}
