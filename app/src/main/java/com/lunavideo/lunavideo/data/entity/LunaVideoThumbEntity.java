package com.lunavideo.lunavideo.data.entity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;

import com.lunavideo.lunavideo.utils.ThreadPool;

import timber.log.Timber;

/**
 * Created by gaowei on 20/06/2017.
 */

public class LunaVideoThumbEntity implements ThreadPool.Job<Bitmap>{
    private long mId;
    private long mDuration;
    private long mHeight;
    private long mWidth;
    private String mTitle;
    private String mMimeType;
    private String mPath;

    private Bitmap mThumbnail;
    private Bitmap mCover;

    public long mResizedHeight;
    public long mResizedWidth;

    public LunaVideoThumbEntity(long id, long duration, long height,
                                long width, String title, String mimeType,
                                String path) {
        mId = id;
        mDuration = duration;
        mHeight = height;
        mWidth = width;
        mTitle = title;
        mMimeType = mimeType;
        mPath = path;
    }

    @Override
    public String toString() {
        String s = "id:" + mId + ", duration:" + mDuration
                + ", height:" + mHeight + ", width:" + mWidth
                + ", title:" + mTitle + ", mime:" + mMimeType
                + ", path:" + mPath;

        if (mThumbnail != null) {
            s += ", thumb height:" + mThumbnail.getHeight()
                    + ", thumb width:" + mThumbnail.getWidth();
        }
        return s;
    }

    public void setThumbnail(Bitmap b, long maxSize) {
        int x = 0;
        int y = 0;
        int h = b.getHeight();
        int w = b.getWidth();
        float ratio;

        if (h >= w) {
            y = (h - w) / 2;
            h = w;
        } else {
            x = (w - h) /2;
            w = h;
        }

        ratio =  maxSize / (float) h;
        Matrix m = new Matrix();

        m.setScale(ratio, ratio);

        Timber.d("x:%d, y:%d, w:%d, h:%d, ratio:%.2f", x, y, h, w, ratio);
        mThumbnail = Bitmap.createBitmap(b, x, y, w, h, m, false);
    }

    public void setThumbnail(Bitmap b) {
        mThumbnail = b;
        Timber.d("thumbnail, W:%d, H:%d", b.getWidth(), b.getHeight());
    }

    public Bitmap getThumbnail () {
        return mThumbnail;
    }

    public long getId() {
        return mId;
    }

    public long getDuration() {
        return mDuration;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bitmap getCover() {
        return mCover;
    }

    @Override
    public Bitmap run(ThreadPool.JobContext jc) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mPath);
        mCover = retriever.getFrameAtTime();
        Timber.d("Cover received, W:%d, H:%d", mCover.getWidth(), mCover.getHeight());
        return null;
    }
}
