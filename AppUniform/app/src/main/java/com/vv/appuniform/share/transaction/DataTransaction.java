package com.vv.appuniform.share.transaction;

import com.vv.appuniform.model.UserModel;
import com.wenxi.learn.data.entity.UserEntity;

/**
 * The type Data transaction.
 * transform entity to model
 */
public class DataTransaction {
    /**
     * Transform user model.
     *
     * @param entity the entity
     * @return the user model
     */
    public static UserModel transform(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserModel model = new UserModel(entity.userId);
        return model;
    }
}
