package com.lunavideo.lunavideo.ui.videogrid;

import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;
import com.lunavideo.lunavideo.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaowei on 19/06/2017.
 */

public interface VideoGridMvpView extends MvpView {
    void onNewVideoThumb(ArrayList<LunaVideoThumbEntity> entities);
}
