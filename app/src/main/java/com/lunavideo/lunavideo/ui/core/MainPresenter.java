package com.lunavideo.lunavideo.ui.core;

import android.content.Context;

import com.lunavideo.lunavideo.LunaVideo;
import com.lunavideo.lunavideo.ui.base.BasePresenter;



/**
 * Created by gaowei on 19/06/2017.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {

	//FIXME
//	DataManager dataManager = DataManager.getInstance(LunaVideo.getInstance());

	void saveLastNavFragment(Context context, String tag) {
		//FIXME
//		DataManager.getInstance(context).getPrefManager().saveLastNavFragment(context, tag);
	}

	String getLastNavFragment(Context context) {
		//FIXME
//		return DataManager.getInstance(context).getPrefManager().getLastNavFragment(context, VideoListFragment.TAG);
		return null;
	}

//	@Override
//	public void detachView() {
//		unsubscribe();
//		super.detachView();
//	}
//
//	private void addSub(Subscription subscription) {
//		subscriptions.add(subscription);
//	}
//
//	private void unsubscribe() {
//		if (subscriptions == null) return;
//		subscriptions.clear();
//		subscriptions = new CompositeSubscription();
//	}

}
