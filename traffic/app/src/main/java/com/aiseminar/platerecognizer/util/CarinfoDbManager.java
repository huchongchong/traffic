package com.aiseminar.platerecognizer.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aiseminar.platerecognizer.model.CarInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CarinfoDbManager {//super(context, name, factory, version)
    private static ContentValues getContentValues(CarInfo info) {
        ContentValues values = new ContentValues();
        values.put(DbCommonDefine.CarInfoTable.Cols.Color, info.color);
        values.put(DbCommonDefine.CarInfoTable.Cols.DATE, info.date);
        values.put(DbCommonDefine.CarInfoTable.Cols.PLATE, info.plate);
        values.put(DbCommonDefine.CarInfoTable.Cols.UUID, info.uuId);
        return values;
    }
  public static void addCarInfo(Context context, CarInfo carInfo){
      CarInfoHelper helper = new CarInfoHelper(context, DbCommonDefine.CarInfoTable.NAME, null, DbCommonDefine.CarInfoTable.VERSION);
      SQLiteDatabase db = helper.getWritableDatabase();
      db.insert(DbCommonDefine.CarInfoTable.NAME,null,getContentValues(carInfo));
      db.close();
      helper.close();
  }
  public void UpdateCarInfo(Context context, CarInfo carInfo){
      String uuidString = carInfo.uuId;
      ContentValues values = getContentValues(carInfo);
      CarInfoHelper helper = new CarInfoHelper(context, DbCommonDefine.CarInfoTable.NAME, null, DbCommonDefine.CarInfoTable.VERSION);
      SQLiteDatabase db = helper.getWritableDatabase();
      db.update( DbCommonDefine.CarInfoTable.NAME, values,
              DbCommonDefine.CarInfoTable.Cols.UUID + " = ?",
              new String[] { uuidString });
  }
  public static List<CarInfo> queryAllCarInfo(Context context){
      ArrayList<CarInfo> list = new ArrayList();
      CarInfoHelper helper = new CarInfoHelper(context, DbCommonDefine.CarInfoTable.NAME, null, DbCommonDefine.CarInfoTable.VERSION);
      SQLiteDatabase db = helper.getReadableDatabase();
      Cursor cursor = db.query(DbCommonDefine.CarInfoTable.NAME, null, null, null, null, null, null);
      try {
          while (cursor.moveToNext()) {
              String uuid = cursor.getString(cursor.getColumnIndex(DbCommonDefine.CarInfoTable.Cols.UUID));
              String color = cursor.getString(cursor.getColumnIndex(DbCommonDefine.CarInfoTable.Cols.Color));
              String date = cursor.getString(cursor.getColumnIndex(DbCommonDefine.CarInfoTable.Cols.DATE));
              String plate = cursor.getString(cursor.getColumnIndex(DbCommonDefine.CarInfoTable.Cols.PLATE));
              CarInfo carInfo = new CarInfo(plate,color,date,uuid);
              list.add(carInfo);
          }
      } finally {
          cursor.close();
      }
      db.close();
      helper.close();
      return list;
  }
    public void delete (Context context,String uuid){
        CarInfoHelper helper = new CarInfoHelper(context, DbCommonDefine.CarInfoTable.NAME, null, DbCommonDefine.CarInfoTable.VERSION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DbCommonDefine.CarInfoTable.NAME,DbCommonDefine.CarInfoTable.Cols.UUID + " = ?",
                new String[] { uuid });
        db.close();
        helper.close();
    }
}
