package com.cleanup.todoc;

import android.app.Application;

import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;
import org.koin.core.logger.Level;

import static com.cleanup.todoc.AppModulesKt.getAppDataTestModule;
import static org.koin.core.context.DefaultContextExtKt.startKoin;

public class TodocAppTest extends Application {

    @Override public void onCreate() {
        super.onCreate();
        KoinApplication koinApplication = KoinAndroidApplication
                .create(this, Level.INFO)
                .modules(getAppDataTestModule());
        startKoin(koinApplication);

    }


}