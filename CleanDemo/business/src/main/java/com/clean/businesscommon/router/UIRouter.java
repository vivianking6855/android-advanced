package com.clean.businesscommon.router;

import android.content.Context;
import android.content.Intent;

import com.clean.home.HomeActivity;

/**
 * Class used to navigate through the several UI in application.
 */
public enum UIRouter {
    INSTANCE;

    /**
     * open home HomeActivity
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void gotoHome(Context context) {
        if (context != null) {
            Intent intentToLaunch = HomeActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
