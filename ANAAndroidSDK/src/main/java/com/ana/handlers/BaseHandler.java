package com.ana.handlers;

import android.os.Handler;

/**
 * Created by Admin on 24-07-2017.
 */

public class BaseHandler extends Handler {

    public static final int NODE_HANDLER_WHAT = 122, VIEWMODEL_WHAT = 123, NODE_SECTION_HANDLER_WHAT = 124;
    public static final int CALLBACK_SECTION_WHAT = 125, CALLBACK_BUTTON_WHAT = 126, NODE_BUTTON_HANDLER_WHAT = 127;

    public ViewModelCallback mCallback;

    public BaseHandler(ViewModelCallback callback) {
        mCallback = callback;
    }

}
