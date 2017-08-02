package com.lunavideo.lunavideo.ui.base;

/**
 * Created by gaowei, 17/06/15.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

}
