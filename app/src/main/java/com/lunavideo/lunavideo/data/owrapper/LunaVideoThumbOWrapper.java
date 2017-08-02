package com.lunavideo.lunavideo.data.owrapper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import com.lunavideo.lunavideo.data.DataManager;
import com.lunavideo.lunavideo.data.IObserableWrapper;
import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by gaowei on 23/06/2017.
 */

public class LunaVideoThumbOWrapper implements IObserableWrapper<ArrayList<LunaVideoThumbEntity>> {

    private Context mContext;
    private long mOffset;
    private static final long COUNT = 100;
    private DataManager mDataMgr;

    public LunaVideoThumbOWrapper (Context context, long offset) {
        mContext = context;
        mOffset = offset;
        mDataMgr = DataManager.getInstance();
    }

    private Observable<ArrayList<LunaVideoThumbEntity>> mVideoThumbEntityObserable = Observable.create((
            emitter)->{
        String projection[] = new String[] {
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DURATION,
                MediaStore.Video.VideoColumns.HEIGHT,
                MediaStore.Video.VideoColumns.WIDTH,
                MediaStore.Video.VideoColumns.TITLE,
                MediaStore.Video.VideoColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.MINI_THUMB_MAGIC,
        };
        ContentResolver resolver = mContext.getContentResolver();
        String orderBy = MediaStore.Video.VideoColumns._ID
                + " DESC LIMIT " +  COUNT  + " OFFSET  " + mOffset;
        Cursor c  = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, orderBy);

        if ((c == null) || (c.getCount() == 0)) {
            emitter.onError(new IllegalArgumentException());
            emitter.onComplete();
            return;
        }

        ArrayList<LunaVideoThumbEntity> entities = new ArrayList();

        while (c.moveToNext()) {
            LunaVideoThumbEntity entity = new LunaVideoThumbEntity(
                    c.getInt(c.getColumnIndex(MediaStore.Video.VideoColumns._ID)),
                    c.getLong(c.getColumnIndex(MediaStore.Video.VideoColumns.DURATION)),
                    c.getLong(c.getColumnIndex(MediaStore.Video.VideoColumns.HEIGHT)),
                    c.getLong(c.getColumnIndex(MediaStore.Video.VideoColumns.WIDTH)),
                    c.getString(c.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)),
                    c.getString(c.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)),
                    c.getString(c.getColumnIndex(MediaStore.Video.VideoColumns.DATA))
            );

            entity.setThumbnail(MediaStore.Video.Thumbnails.getThumbnail(
                    resolver, entity.getId(), MediaStore.Video.Thumbnails.MINI_KIND, null));

            //entity.setThumbnail(Media.getInstance().getVideoFrame(entity.path));

            entities.add(entity);
        }

        emitter.onNext(entities);
        emitter.onComplete();
    });

    @Override
    public Observable<ArrayList<LunaVideoThumbEntity>> get() {
        return mVideoThumbEntityObserable;
    }
}
