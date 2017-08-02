package com.lunavideo.lunavideo.ui.videogrid;

import com.lunavideo.lunavideo.data.DataManager;
import com.lunavideo.lunavideo.data.DatabaseManager;
import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;
import com.lunavideo.lunavideo.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by gaowei on 19/06/2017.
 */

public class VideoGridPresenter extends BasePresenter<VideoGridMvpView> {
    private DataManager mDataMgr = DataManager.getInstance();

    private DisposableObserver<ArrayList<LunaVideoThumbEntity>> mVideoThumbEntityObserver
            = new DisposableObserver<ArrayList<LunaVideoThumbEntity>>() {
        @Override
        public void onNext(@NonNull ArrayList<LunaVideoThumbEntity> entities) {
            getMvpView().onNewVideoThumb(entities);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Timber.e(e.toString());
        }

        @Override
        public void onComplete() {
            Timber.d("onComplete");
        }
    };

    private CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    public void attachView(VideoGridMvpView mvpView) {
        super.attachView(mvpView);

        Timber.d("attachView");

        mDisposable.add(mDataMgr.getVideoThumbEntityObserable(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mVideoThumbEntityObserver));
    }

    @Override
    public void detachView() {
        super.detachView();
        mDisposable.clear();
    }


}
