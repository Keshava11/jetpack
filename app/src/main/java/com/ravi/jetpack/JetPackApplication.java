package com.ravi.jetpack;

import android.app.Application;

import com.ravi.jetpack.api.APIClient;
import com.ravi.jetpack.api.ApiInterface;

public class JetPackApplication extends Application {

    private static JetPackApplication sInstance;
    private static ApiInterface sApiInterface;

    public static ApiInterface getApiInterface() {
        return sApiInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        sApiInterface = APIClient.getClient().create(ApiInterface.class);
    }

    public static synchronized JetPackApplication getInstance() {
        return sInstance;
    }
}
