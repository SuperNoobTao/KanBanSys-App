package com.sorcererxw.demo.gridwebview;

import android.app.Application;

import timber.log.Timber;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/2/28
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
