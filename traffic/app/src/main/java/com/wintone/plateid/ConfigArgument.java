package com.wintone.plateid;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ConfigArgument {
    public static final String TAG = "ConfigArgument";
    public int armpolice = 4;
    public int armpolice2 = 17;
    public int bIsAutoSlope = 1;
    TH_PlateIDCfg c_Config = new TH_PlateIDCfg();
    String cfg = "";
    String[] cfgs;
    public  int dFormat = 0;
    public int embassy = 13;
    public int individual = 0;
    public int nContrast = 0;
    public int nOCR_Th = 2;
    public int nPlateLocate_Th = 5;
    public int nSlopeDetectRange = 0;
    public  int onlylocation = 15;
    public int onlytworowyellow = 11;
    public String szProvince = "";
    public int tractor = 9;
    public  int tworowarmy = 7;
    public  int tworowyellow = 3;

    public TH_PlateIDCfg getTH_PlateIDCfg()
    {
        return this.c_Config;
    }
}
