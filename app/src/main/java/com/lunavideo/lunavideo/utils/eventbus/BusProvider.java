package com.lunavideo.lunavideo.utils.eventbus;

import android.os.Handler;
import android.os.Looper;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by gaowei on 16/06/2017.
 */

public class BusProvider {

    static private Bus helper;

    static public Bus getInstance() {
        synchronized (BusProvider.class) {
            if (helper == null) {
                helper = new Bus();
            }
        }
        return helper;
    }

    static public class Bus {
        private final EventBus bus;

        public Bus() {
            bus = EventBus.getDefault();
        }

        public void register(Object subscriber) {
            bus.register(subscriber);
        }

        public void unregister(Object subscriber) {
            bus.unregister(subscriber);
        }

        public void post(Object event) {
            bus.post(event);
        }

        public void postSticky(Object event) {
            bus.postSticky(event);
        }

        public void postOnMainThread(Object event) {
            new Handler(Looper.getMainLooper()).post(() -> bus.post(event));
        }
    }

}
