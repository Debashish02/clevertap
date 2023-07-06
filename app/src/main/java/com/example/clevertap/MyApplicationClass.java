package com.example.clevertap;
import com.clevertap.android.sdk.ActivityLifecycleCallback;

import android.app.Application;

public class MyApplicationClass extends Application {

    @Override
    public void onCreate(){


// Must be called before super.onCreate()
    ActivityLifecycleCallback.register(this);   //added by CleverTap Assistant
        super.onCreate();
    }

}
