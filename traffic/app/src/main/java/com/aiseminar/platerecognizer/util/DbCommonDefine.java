package com.aiseminar.platerecognizer.util;

/**
 * Created by Administrator on 2017/6/12.
 */

public class DbCommonDefine {
    public static final class CarInfoTable {
        public static final String NAME = "CarInfo";
        public static final int VERSION = 1; //数据库版本
        public static final class Cols {
            public static final String PLATE = "plate";//车牌
            public static final String DATE = "date";//时间
            public static final String Color = "color";//车牌颜色
            public static final String UUID = "uuid";//id
        }
    }
}
