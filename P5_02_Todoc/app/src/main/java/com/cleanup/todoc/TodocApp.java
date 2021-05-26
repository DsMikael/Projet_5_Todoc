package com.cleanup.todoc;

import android.app.Application;
import timber.log.Timber;

public class TodocApp extends Application {
    @Override public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}