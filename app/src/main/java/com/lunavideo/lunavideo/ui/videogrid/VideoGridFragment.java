package com.lunavideo.lunavideo.ui.videogrid;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.lunavideo.lunavideo.R;
import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;
import com.lunavideo.lunavideo.ui.base.fragment.MvpBaseFragment;
import com.lunavideo.lunavideo.utils.Future;
import com.lunavideo.lunavideo.utils.ThreadPool;
import com.lunavideo.lunavideo.widget.LunaVideoGridView;

import java.util.ArrayList;
import java.util.HashMap;

import com.kennyc.view.MultiStateView;



import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by gaowei on 19/06/2017.
 */

public class VideoGridFragment extends MvpBaseFragment<VideoGridMvpView, VideoGridPresenter>
        implements VideoGridMvpView, AbsListView.OnItemClickListener, AbsListView.OnScrollListener{

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @BindView(R.id.grid_view)
    LunaVideoGridView mGridView;

    @BindView(R.id.anim_thumb)
    ImageView mAnimThumb;

    VideoGridAdapter mAdapter;

    HashMap<LunaVideoThumbEntity, Future<Bitmap>> mEntities = new HashMap<>();
    ThreadPool mThreadPool = new ThreadPool();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Timber.i("onCreate");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        Timber.d("onCreateView");

        mAdapter = new VideoGridAdapter(getActivity(), 0);

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @NonNull
    @Override
    protected VideoGridMvpView mvpView() {
        return this;
    }


    @Override
    protected int layoutResId() {
        return R.layout.fragment_video_grid;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((mAnimThumb.getVisibility() == View.VISIBLE)
                && keyCode == KeyEvent.KEYCODE_BACK) {
            mAnimThumb.setVisibility(View.GONE);
            mMultiStateView.setVisibility(View.VISIBLE);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNewVideoThumb(ArrayList<LunaVideoThumbEntity> entities) {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.addAll(entities);
        mAdapter.notifyDataSetChanged();
        for(LunaVideoThumbEntity entity : entities) {
            // get first frame in thread pool
            Future<Bitmap> f = mThreadPool.submit(entity);
            mEntities.put(entity, f);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Timber.i("onItemClick, view.X:%.2f, view.Y:%.2f, Width:%d, Height:%d",
                view.getX(), view.getY(), view.getWidth(), view.getHeight());

        LunaVideoThumbEntity entity = (LunaVideoThumbEntity) parent.getItemAtPosition(position);

        RelativeLayout container = (RelativeLayout) view;
        Future<Bitmap> f = mEntities.get(entity);
        if (f.isDone()) {
            mAnimThumb.setImageBitmap(entity.getCover());
        } else {
            mAnimThumb.setImageBitmap(f.get());
        }

//        ImageView v = (ImageView)container.findViewById(R.id.thumb);


        mAnimThumb.setVisibility(View.VISIBLE);
        mMultiStateView.setVisibility(View.INVISIBLE);
    }


    private Animation getExitAnimation(AdapterView<?> parent, View view) {
        AnimationSet s = new AnimationSet(true);
        s.addAnimation(getScaleAnimation(parent.getMeasuredWidth(), parent.getMeasuredHeight(), view));
        //s.addAnimation(getTranslateAnimation(parent.getMeasuredWidth(), parent.getMeasuredHeight(), view));

        s.setDuration(5000);
        s.setInterpolator(new DecelerateInterpolator(1.0f));

        return s;
    }


    private Animation getTranslateAnimation(int parentWidth, int parentHeight, View view) {
        float fromX = 0.0f;
        float fromY = 0.0f;

        float toX = (parentWidth - view.getWidth()) / 2 - view.getX();
        float toY = (parentHeight - view.getHeight()) / 2 - view.getY();

        return new TranslateAnimation(fromX, toX, fromY, toY);
    }

    private Animation getScaleAnimation(int parentWidth, int parentHeight, View view) {
        float toXFactor = 2.0f;//(float) parentWidth / view.getMeasuredWidth();
        float toYFactor = 2.0f;//(float) parentHeight / view.getMeasuredHeight();
        Timber.d("xFactor:%.2f, yFactor:%.2f", toXFactor, toYFactor);
        Animation a = new ScaleAnimation(1.0f, toXFactor, 1.0f, toYFactor,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        return a;
    }

    private void showVideoThumbFragment(View view, LunaVideoThumbEntity entity){
        FragmentTransaction t = getFragmentManager().beginTransaction();
        //t.add(R.id.core_fragment,
        //        new VideoThumbFragment(entity, view.getX(), view.getY()));
        t.hide(this);
        t.commitAllowingStateLoss();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

    }

}
