package com.lunavideo.lunavideo.data;

import android.graphics.Bitmap;

import com.lunavideo.lunavideo.data.owrapper.LunaVideoFrameOWrapper;

import io.reactivex.Observable;

/**
 * Created by gaowei on 29/06/2017.
 */

class MediaManager {
    private static final MediaManager ourInstance = new MediaManager();

    static MediaManager getInstance() {
        return ourInstance;
    }

    private MediaManager() {
    }

    public Observable<Bitmap> getVideoFrameBitmapObservable(String path) {
        return new LunaVideoFrameOWrapper(path).get();
    }
}
