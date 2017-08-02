package com.lunavideo.lunavideo.ui.core;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;

import butterknife.BindView;

import com.lunavideo.lunavideo.R;
import com.lunavideo.lunavideo.ui.base.activity.MvpBaseActivity;
import com.lunavideo.lunavideo.ui.base.fragment.BaseFragment;
import com.lunavideo.lunavideo.ui.videogrid.VideoGridFragment;
import com.lunavideo.lunavideo.utils.PermissionUtils;
import com.lunavideo.lunavideo.utils.eventbus.BusProvider;
import com.lunavideo.lunavideo.utils.eventbus.Events;

import java.util.ArrayList;


/**
 * Created by gaowei on 19/06/2017.
 */

public class MainActivity extends MvpBaseActivity<MainMvpView, MainPresenter>
        implements MainMvpView {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    BaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        mFragment = new VideoGridFragment();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.add(R.id.core_fragment, mFragment);
        t.commitAllowingStateLoss();

        checkPermissionIfNecessary();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mFragment.onKeyDown(keyCode, event) == true) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void checkPermissionIfNecessary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> grantList = new ArrayList<>();
            if (!PermissionUtils.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                grantList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            String[] grantArray = grantList.toArray(new String[grantList.size()]);
            if (grantArray != null && grantArray.length > 0) {
                ActivityCompat.requestPermissions(this, grantArray, 1);
            }
        }
    }

    @NonNull
    @Override
    protected MainMvpView mvpView() {
        return this;
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
