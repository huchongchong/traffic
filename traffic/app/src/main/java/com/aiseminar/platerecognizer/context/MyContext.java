package com.aiseminar.platerecognizer.context;

import android.os.Environment;
import android.util.Log;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liulin on 2017/2/17.
 */

public class MyContext {
    public static MyContext myContext;

    public static MyContext getInstance(){
        if(myContext==null){
            myContext = new MyContext();
        }
        return myContext;
    }

    public static void setMyContext(MyContext context){
        myContext = context;
    }
    public static final int PAGE_LOGIN = 0;
    public static final int PAGE_MAIN = 1;
    public static final int PAGE_MAP = 2;
    public static final int PAGE_FORM = 3;
    public static final int PAGE_ORGER_PHOTO = 4;
    public static final int PAGE_PUNISH_PHOTO = 5;
    public static final int PAGE_HISTORY = 6;
    public static final int PAGE_EXCEPTION = 7;

    public static int CURRENT_PAGE = 0;

    public Report report = new Report();
    public int pos = 0;

    public  Order order = new Order();
    public  int order_pos = 0;

    public  int toException = 0;

    public  int playerId;
    public  String playerName="柳林";

    public  String regionName;
    public int regionId;
    public  double x = 0d;
    public static double y = 0d;

    public String path;//临时文件位置
    public Map<String,Order> orderMap = new HashMap<>();

    public int orderIndex = 1;

    public String createOrderId(){
        String prefix = new SimpleDateFormat("yyMMDD").format(new Date());
        String temp = String.valueOf(orderIndex);
        if(temp.length()==1){
            temp = "00"+temp;
        }
        if(temp.length()==2){
            temp = "0"+temp;
        }
        return prefix+temp;
    }
    public String plateColor;
    public String plateNum;
    public static class Order{
        private String  orderId;
        private String plateNum;
        private String plateColor;
        private String createTime;
        private String carType;
        private int regionId;
        private String pic1;
        private String pic2;
        private String pic3;
        private int sync = 0;
        public String getPlateNum() {
            return plateNum;
        }

        public void setPlateNum(String plateNum) {
            this.plateNum = plateNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }


        public String getPlateColor() {
            return plateColor;
        }

        public void setPlateColor(String plateColor) {
            this.plateColor = plateColor;
        }

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public int getSync() {
            return sync;
        }

        public void setSync(int sync) {
            this.sync = sync;
        }
    }

    public static class Report{
        private String content;
        private String addr;
        private String pic1;
        private String pic2;
        private String pic3;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }
    }

    public static void save(){
        Gson gson = new Gson();
        String str = gson.toJson(myContext);

        File fileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"traffic");
        if (! fileDir.exists()) {
            if (! fileDir.mkdirs()) {
                Log.d("PlateRcognizer", "failed to create directory");
            }
        }
        File configFile = new File(fileDir.getPath() + File.separator + "trafficUser.txt");
        try {
            FileOutputStream fos = new FileOutputStream(configFile);
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void load() {
        File fileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"traffic");
        if (! fileDir.exists()) {
            ;return;
        }
        File configFile = new File(fileDir.getPath() + File.separator + "trafficUser.txt");
        try {
            FileInputStream fis = new FileInputStream(configFile);
            byte[] arr = new byte[fis.available()];
            fis.read(arr);
            String str = new String(arr);
            Gson gson = new Gson();
            MyContext context = gson.fromJson(str,MyContext.class);
            setMyContext(context);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
