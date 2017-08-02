package com.lunavideo.lunavideo.data;

import android.graphics.Bitmap;

import com.lunavideo.lunavideo.LunaVideo;
import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by gaowei on 20/06/2017.
 */

public class DataManager  {
    private static final DataManager sInstance = new DataManager();

    private DatabaseManager mDBmanager = DatabaseManager.getInstance(LunaVideo.getInstance());
    private MediaManager mMediaManager = MediaManager.getInstance();

    public static DataManager getInstance() {
        return sInstance;
    }

    private DataManager() {
    }


    public Observable<ArrayList<LunaVideoThumbEntity>> getVideoThumbEntityObserable(long offset) {
        return mDBmanager.getVideoThumbEntityObserable(offset);
    }

    public Observable<Bitmap> getVideoFrameBitmapObserable(String path) {
        return mMediaManager.getVideoFrameBitmapObservable(path);
    }
}
