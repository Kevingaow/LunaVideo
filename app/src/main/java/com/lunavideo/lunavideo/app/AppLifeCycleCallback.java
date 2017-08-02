package com.lunavideo.lunavideo.app;

import android.app.Application;


/**
 * Created by gaowei on 15/06/2017.
 */
public interface AppLifeCycleCallback {
    void onCreate(Application application);

    void onTerminate(Application application);
}