package com.lunavideo.lunavideo.data;

import io.reactivex.Observable;

/**
 * Created by gaowei on 23/06/2017.
 */

public interface IObserableWrapper<T> {

    Observable<T> get();
}
