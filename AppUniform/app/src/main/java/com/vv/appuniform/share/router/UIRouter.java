package com.vv.appuniform.share.router;

import android.content.Context;
import android.content.Intent;

import com.vv.appuniform.view.activity.MainActivity;

/**
 * Class used to navigate through the several UI in application.
 */
public enum UIRouter {
    INSTANCE;

    /**
     * open home MainActivity
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void openHome(Context context) {
        if (context != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
