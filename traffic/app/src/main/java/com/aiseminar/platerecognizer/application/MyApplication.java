package com.aiseminar.platerecognizer.application;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.aiseminar.platerecognizer.service.LocationService;
import com.aiseminar.platerecognizer.util.preDefiniation;
import com.aiseminar.platerecognizer.util.preDefiniation.TransferMode;
import com.baidu.mapapi.SDKInitializer;

import rego.printlib.export.regoPrinter;

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
    private regoPrinter printer;
    private int myState = 0;
    private String printName="RG-MTP58B";
    preDefiniation preDefiniations;
    private TransferMode printmode = TransferMode.TM_NONE;
    private boolean labelmark = true;

    public regoPrinter getObject() {
        return printer;
    }

    public void setObject() {
        printer = new regoPrinter(this);
    }

    public String getName() {
        return printName;
    }

    public void setName(String name) {
        printName = name;
    }
    public void setState(int state) {
        myState = state;
    }

    public int getState() {
        return myState;
    }

    public void setPrintway(int printway) {

        switch (printway) {
            case 0:
                printmode = TransferMode.TM_NONE;
                break;
            case 1:
                printmode = TransferMode.TM_DT_V1;
                break;
            default:
                printmode = TransferMode.TM_DT_V2;
                break;
        }

    }

    public int getPrintway() {
        return printmode.getValue();
    }

    public boolean getlabel() {
        return labelmark;
    }

    public void setlabel(boolean labelprint) {
        labelmark = labelprint;
    }
}
