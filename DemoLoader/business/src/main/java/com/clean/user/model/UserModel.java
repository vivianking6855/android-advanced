package com.clean.user.model;

import android.support.annotation.StringRes;

import com.alibaba.fastjson.JSON;

/**
 * The type User model.
 */
public class UserModel {
    public final String userId;
    public String coverUrl;
    public String fullName;
    public String description;

    // load status
    public final static int SUCCESS = 1;
    public final static int FAIL = 0;
    public int status = 0;
    public String error;

    /**
     * Instantiates a new User model.
     *
     * @param userId the user id
     */
    public UserModel(String userId) {
        this.userId = userId;
        if (userId != null) {
            status = SUCCESS;
        }
    }

    public UserModel(String userId, String error) {
        this.userId = userId;
        this.error = error;
        if (userId != null) {
            status = SUCCESS;
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
