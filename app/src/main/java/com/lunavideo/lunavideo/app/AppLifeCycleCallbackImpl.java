package com.lunavideo.lunavideo.app;

import android.app.Application;
import android.util.Log;

import com.lunavideo.lunavideo.BuildConfig;
import com.lunavideo.lunavideo.utils.ReleaseLogTree;


import timber.log.Timber;


/**
 * Created by gaowei on 15/06/2017.
 */
public class AppLifeCycleCallbackImpl implements AppLifeCycleCallback {
    private static AppLifeCycleCallback mAppLifeCycleCallback;

    public static AppLifeCycleCallback getInstance() {
        if (mAppLifeCycleCallback == null) {
            mAppLifeCycleCallback = new AppLifeCycleCallbackImpl();
        }
        return mAppLifeCycleCallback;
    }

    @Override
    public void onCreate(Application application) {

        // init log
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseLogTree());
        }
    }

    @Override
    public void onTerminate(Application application) {
    }
}