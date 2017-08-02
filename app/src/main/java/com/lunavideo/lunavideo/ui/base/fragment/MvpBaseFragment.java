package com.lunavideo.lunavideo.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lunavideo.lunavideo.ui.base.BasePresenter;
import com.lunavideo.lunavideo.ui.base.MvpView;

import java.lang.reflect.ParameterizedType;

import timber.log.Timber;


/**
 * Created by gaowei, 17/06/16.
 */

public abstract class MvpBaseFragment<V extends MvpView, P extends BasePresenter<V>> extends BaseFragment {

	protected P mPresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
		if (mPresenter == null)
			throw new NullPointerException("Please override 'protected P newPresenter()' on Fragment class.");
        mPresenter.attachView(mvpView());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		if (mPresenter != null)
            mPresenter.detachView();

		super.onDestroy();
	}

	protected P createPresenter() {
		Class<P> pClass = (Class<P>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		try {
			return pClass.newInstance();
		} catch (java.lang.InstantiationException e) {
			Timber.e(e.getMessage());
		} catch (IllegalAccessException e) {
			Timber.e(e.getMessage());
		}

		return null;
	}

	/**
	 * Get the implemented mvp presenter.
	 *
	 * @return mvp presenter.
	 */
	@NonNull
	protected P getMvpPresenter() {
		return mPresenter;
	}

	/**
	 * Get the implemented mvp view.
	 *
	 * @return mvp view.
	 */
	@NonNull
	protected abstract V mvpView();
}
