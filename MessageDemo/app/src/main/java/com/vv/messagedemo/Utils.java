package com.vv.messagedemo;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by vivian on 2017/2/8.
 */

public class Utils {

    public static void showHint(Context context, String title, String content) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(title == null ? "Hint" : title).setMessage(content).create()
                .show();
    }
}
