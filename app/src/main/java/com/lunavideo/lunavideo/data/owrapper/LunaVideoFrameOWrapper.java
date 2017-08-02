package com.lunavideo.lunavideo.data.owrapper;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.lunavideo.lunavideo.data.IObserableWrapper;

import io.reactivex.Observable;

/**
 * Created by gaowei on 29/06/2017.
 */

public class LunaVideoFrameOWrapper implements IObserableWrapper<Bitmap>{

    String mPath;

    private Observable<Bitmap> mObservable = Observable.create((emitter)->{
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mPath);
        Bitmap b = retriever.getFrameAtTime(0);
        emitter.onNext(b);
        emitter.onComplete();
    });


    public LunaVideoFrameOWrapper(String path) {
        mPath = path;
    }

    @Override
    public Observable<Bitmap> get() {
        return mObservable;
    }
}
