package com.lunavideo.lunavideo.ui.base.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lunavideo.lunavideo.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;


/**
 * Created by gaowei, 17/06/16.
 */

public abstract class BaseFragment extends RxFragment {

    private boolean foundViewPager = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflate(layoutResId(), inflater, container);
        ButterKnife.bind(this, view);

        // found viewPager or not
        //FIXME
        //foundViewPager = view.findViewById(R.id.viewPager) != null;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint())
            return;

        if (shouldLogScreen()) {
            //FIXME
//            EventLogger.getInstance().logScreen(getClass().getName());
        }
    }


    protected boolean shouldLogScreen() {
        return !foundViewPager;
    }

    public View inflate(@LayoutRes int layoutResId, LayoutInflater layoutInflater, ViewGroup container) {
        return layoutInflater.inflate(layoutResId, container, false);
    }

    /**
     * Implement it on fragment.
     * @return The layout resource id to be inflated on the fragment.
     */
    @LayoutRes
    protected abstract int layoutResId();

    protected boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
