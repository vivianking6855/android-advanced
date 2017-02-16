package com.vv.ipc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by vivian on 2017/2/14.
 */

public final class Const {
    public final static String KEY_TO_SECOND = "key_to_second";
    public final static String KEY_FROM_CLIENT = "key_from_client";
    public final static String KEY_FROM_SERVICE= "key_from_service";

    public final static int MSG_FROM_CLIENT = 10000;
    public final static int MSG_FROM_SERVICE = 10001;
    public final static int MSG_UPDATE_UI = 10002;

    public final static DateFormat DATA_FORMAT = new SimpleDateFormat("mm:ss");

    public final static String PERMISSION_ACCESS_BOOK_SERVICE = "com.vv.ipc.permission.ACCESS_BOOK_SERVICE";

}
