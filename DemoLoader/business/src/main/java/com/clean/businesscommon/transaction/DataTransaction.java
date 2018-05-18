package com.clean.businesscommon.transaction;

import com.clean.user.model.UserModel;
import com.learn.data.entity.UserEntity;

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
    public static UserModel transformUser(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserModel model = new UserModel(entity.userId);
        return model;
    }
}
