package com.wintone.plateid;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6.
 */

public class TH_PlateIDResult implements Serializable{
    int bottom;
    String color = "";
    int left;
    String license = "";
    int nBright;
    int nCarBright = 0;
    int nCarColor = 0;
    int nColor;
    int nConfidence;
    int nDirection;
    int nTime;
    int nType;
    String pbyBits = "";
    String reserved = "";
    int right;
    int top;
    public int getBottom() {
        return bottom;
    }

    public String getColor() {
        return color;
    }

    public int getLeft() {
        return left;
    }

    public String getLicense() {
        return license;
    }

    public int getnBright() {
        return nBright;
    }

    public int getnCarBright() {
        return nCarBright;
    }

    public int getnCarColor() {
        return nCarColor;
    }

    public int getnColor() {
        return nColor;
    }

    public int getnConfidence() {
        return nConfidence;
    }

    public int getnDirection() {
        return nDirection;
    }

    public int getnTime() {
        return nTime;
    }

    public int getnType() {
        return nType;
    }

    public String getPbyBits() {
        return pbyBits;
    }

    public String getReserved() {
        return reserved;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }
}
