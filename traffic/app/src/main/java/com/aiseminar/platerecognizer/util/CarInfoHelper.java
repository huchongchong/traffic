package com.aiseminar.platerecognizer.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CarInfoHelper extends SQLiteOpenHelper{
    public CarInfoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CarInfoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbCommonDefine.CarInfoTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DbCommonDefine.CarInfoTable.Cols.PLATE + " text, " +
                DbCommonDefine.CarInfoTable.Cols.DATE + " text, " +
                DbCommonDefine.CarInfoTable.Cols.UUID + " text, " +
                DbCommonDefine.CarInfoTable.Cols.Color + " text"+
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE "+DbCommonDefine.CarInfoTable.NAME+" ADD phone VARCHAR(12)"); //往表中增加一列
    }
}
