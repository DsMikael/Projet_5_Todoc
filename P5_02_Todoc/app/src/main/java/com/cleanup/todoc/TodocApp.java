package com.cleanup.todoc;

import android.app.Application;

import org.koin.android.BuildConfig;
import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;
import org.koin.core.logger.Level;

import timber.log.Timber;

import static com.cleanup.todoc.AppModulesKt.getAppDataModule;
import static com.cleanup.todoc.AppModulesKt.getAppDataTestModule;
import static org.koin.core.context.DefaultContextExtKt.startKoin;

public class TodocApp extends Application {

    @Override public void onCreate() {
        super.onCreate();

        KoinApplication koinApplication = KoinAndroidApplication
                .create(this, Level.INFO)
                .modules(getAppDataModule(), getAppDataTestModule());
//                .modules(appDataModule, appDataTestModule);
        startKoin(koinApplication);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }


}