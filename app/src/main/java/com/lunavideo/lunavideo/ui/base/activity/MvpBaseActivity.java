package com.lunavideo.lunavideo.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lunavideo.lunavideo.ui.base.BasePresenter;
import com.lunavideo.lunavideo.ui.base.MvpView;

import java.lang.reflect.ParameterizedType;

import timber.log.Timber;


/**
 * Created by gaowei, 17/06/15.
 */

public abstract class MvpBaseActivity<V extends MvpView, P extends BasePresenter<V>> extends BaseActivity {

	private P mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPresenter = createPresenter();
		if (mPresenter == null)
			throw new NullPointerException("Please call 'component.inject(this)' on onCreateComponent method.");
		mPresenter.attachView(mvpView());
	}

	@Override
	protected void onDestroy() {
		mPresenter.detachView();

		super.onDestroy();
	}

	protected P createPresenter() {
		Class<P> pClass = (Class<P>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		try {
			return pClass.newInstance();
		} catch (InstantiationException e) {
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
