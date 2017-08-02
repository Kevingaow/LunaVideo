package com.lunavideo.lunavideo.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;
import com.lunavideo.lunavideo.data.owrapper.LunaVideoThumbOWrapper;
import com.lunavideo.lunavideo.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import timber.log.Timber;


/**
 * Created by gaowei on 20/06/2017.
 */

public class DatabaseManager {

    private static final Uri AUDIO_URL = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    private static DatabaseManager sInstance;
    private Context mContext;

    public static DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context);
        }
        return sInstance;
    }

    private DatabaseManager(Context context) {
        mContext = context;

    }

    Observable<ArrayList<LunaVideoThumbEntity>> getVideoThumbEntityObserable(long offset) {
        return new LunaVideoThumbOWrapper(mContext, offset).get();
    }

}




