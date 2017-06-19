package com.aiseminar.platerecognizer.model;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CarInfo {
    public String plate;
    public String color;
    public String date;
    public String uuId;

    public CarInfo(String plate, String color, String date, String uuId) {
        this.plate = plate;
        this.color = color;
        this.date = date;
        this.uuId = uuId;
    }
}
