package com.lunavideo.lunavideo;

import android.app.Application;

import com.lunavideo.lunavideo.app.AppLifeCycleCallbackImpl;

/**
 * Created by gaowei on 15/06/2017.
 */
public class LunaVideo extends Application {
    private static LunaVideo mInstance;

    public static LunaVideo getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppLifeCycleCallbackImpl.getInstance().onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppLifeCycleCallbackImpl.getInstance().onTerminate(this);
    }
}