package com.aiseminar.platerecognizer.application;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.aiseminar.platerecognizer.service.LocationService;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2017/4/20.
 */

public class MyApplication extends Application {
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
//        SDKInitializer.initialize(getApplicationContext());/***

        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

    }
}
