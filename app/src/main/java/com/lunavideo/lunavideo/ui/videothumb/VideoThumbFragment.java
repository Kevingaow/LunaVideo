package com.lunavideo.lunavideo.ui.videothumb;


import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lunavideo.lunavideo.R;
import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;
import com.lunavideo.lunavideo.ui.base.fragment.MvpBaseFragment;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by gaowei on 27/06/2017.
 */

public class VideoThumbFragment extends MvpBaseFragment<VideoThumbView, VideoThumbPresenter>
        implements VideoThumbView {

    @BindView(R.id.thumb_video)
    ImageView mVideoThumbImage;

    LunaVideoThumbEntity mEntity;
    float mPosX;
    float mPosY;
    int mParentWidth;
    int mParentHeight;

    public VideoThumbFragment(LunaVideoThumbEntity entity, float xPos, float yPos) {
        mEntity = entity;
        mPosX = xPos;
        mPosY = yPos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);


        mVideoThumbImage.setImageBitmap(mEntity.getThumbnail());
        mParentWidth = container.getMeasuredWidth();
        mParentHeight = container.getMeasuredHeight();
     //   mVideoThumbImage.setX(mPosX);
     //   mVideoThumbImage.setY(mPosY);
//        RelativeLayout parent = (RelativeLayout) rootView.getParent();
        Timber.d("W:%d, H:%d", container.getMeasuredWidth(), container.getMeasuredHeight());

        return rootView;
    }

    @NonNull
    @Override
    protected VideoThumbView mvpView() {
        return this;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_video_thumb;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Timber.d("onCreateAnimation, transit:%d, enter:%s, nextAnim:%d",
                transit, Boolean.toString(enter), nextAnim);

        Timber.d("H:%d, W:%d", mVideoThumbImage.getMeasuredHeight(),
                mVideoThumbImage.getMeasuredWidth());

        Animation zoomAnimation = new ScaleAnimation(0.2f, 1.0f, 0.2f, 1.0f,
                Animation.RELATIVE_TO_SELF, mPosX / mParentWidth,
                Animation.RELATIVE_TO_SELF, mPosY / mParentHeight);

        zoomAnimation.setDuration(200);
        zoomAnimation.setInterpolator(new DecelerateInterpolator(1.0f));

        return zoomAnimation;
        //return super.onCreateAnimation(transit, enter, nextAnim);
    }
}
