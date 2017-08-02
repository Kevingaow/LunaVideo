package com.lunavideo.lunavideo.ui.base.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by gaowei, 17/06/15.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Support fabric
        // FIXME
//        Fabric.with(this);

        int layoutResId = layoutResId();
        if (layoutResId > 0) {
            setContentView(layoutResId);
            ButterKnife.bind(this);
        }

		// Log screen
		String screenName = getLogScreenName();
		if (!TextUtils.isEmpty(screenName)) {
            //FIXME
//			EventLogger.getInstance().logScreen(screenName);
		}
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected String getLogScreenName() {
        return null;
    }

    /**
     * Implement it on activity.
     * @return The layout resource id to be set as the content view on the activity.
     */
    @LayoutRes
    protected abstract int layoutResId();

    protected boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
