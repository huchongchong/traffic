package com.aiseminar.platerecognizer.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.application.MyApplication;
import com.aiseminar.platerecognizer.views.ComDialog;
import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;


public class PrintActivity extends AppCompatActivity implements View.OnClickListener {

    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           if(msg.what==1){
               ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(PrintActivity.this,
                       android.R.layout.simple_spinner_item, btName);
               mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spinner.setAdapter(mAdapter);
           }else if(msg.what==2){
               loading.animateDismiss();
           }else if(msg.what==3){//链接并打印
               loading.animateDismiss();
               Toast.makeText(PrintActivity.this,"连接打印机成功",Toast.LENGTH_SHORT).show();
               printLable();
           }else if(msg.what==4){
               loading.animateDismiss();
               Toast.makeText(PrintActivity.this,"连接打印机失败",Toast.LENGTH_SHORT).show();
           }else if(msg.what==5){
               if(loading.isShowing()) {
                   loading.animateDismiss();
               }
//               animateDismissToast.makeText(PrintActivity.this,"连接打印机成功",Toast.LENGTH_SHORT).show();
//               printText();
               printLable();
           }
        }
    };
    private Spinner spinner;
    private MyApplication context;
    private ArrayList<String> printName;
    private ArrayList<String> btName = new ArrayList<>();//蓝牙设备名称
    private ArrayList<String> btId = new ArrayList<>();//蓝牙设备ID
    private boolean mBconnect;
    private int state;
    private String btMaxId;
    private ComDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        context = (MyApplication) getApplicationContext();
        context.setObject();
        initView();
        initBtData();
    }

    private void initBtData() {
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        if(bta==null){
            getDevice();
            return;
        }
        if(bta.getState()==BluetoothAdapter.STATE_ON){
            getDevice();
            return;
        }
        if(bta.getState()==BluetoothAdapter.STATE_TURNING_OFF||bta.getState()==BluetoothAdapter.STATE_OFF){
            bta.enable();
            return;
        }
    }

    private void printLable(){
        //lable model
        /**
         * objCode - 某个打开的端口对象
         width - 标签的宽度 384(58mm纸宽)或576(80mm纸宽)
         height - 标签的高度
         xStart - 整个标签的水平偏移量，如在MLP80A上打印58的纸， offset的值可以写为96
         page - 打印标签的数量
         */
        context.getObject().CPCL_PageStart(context.getState(),576,350,0,1);//
        //pic model
        /**
         * 进入打印模式，连接成功后调用本函数去开启打印功能，调用以"DRAW_"为前缀的绘图打印函数，其他以对应语言的文本方式打印 最后调用CON_PageEnd() 结束页面文本或图形数据缓存；CON_PageSend() 发送缓存数据
         参数:
         objCode - 某个打开的端口对象
         graphicMode - 是否采用图形模式打印，false采用打印机内置字体打印内容，随后的内容打印应调用文本打印函数; true采用图形方式打印，随后的内容打印可以调用文本打印函数(标签机)或图形打印函数
         width - 图形模式或标签机器的文本模式下最大打印宽度，384(58mm纸宽)或576(80mm纸宽)
         height - 图形模式或标签机器的文本模式下最大打印高度，大小没有限制的，但是当进入图形模式后，高度越高表示缓存的数据量越大，当高度超过1500时可以会引起程序缓存异常
         返回:
         1 进入页打印模式成功, 0 进入页打印模式失败
         */
        context.getObject().CON_PageStart(context.getState(), true, 250, 250);//250,80

        /**
         * 打印指定字符串_CPCl
            参数:
            objCode - 发送至某个打开的端口对象
            posX - 起始位置x轴
            posY - 起始位置y轴
            width - 字体宽度放大倍数(0,7)
            height - 字体高度放大倍数(0,7)
            rotate - 文字旋转0,90，180
            font - 字体 24 （字符(12*24)，汉字(24*24)） 55（字符(8*16)，汉字(16*16)）
            strPrint - 字符串数据
            encode - 字符串编码格式
         */
        context.getObject().CPCL_PrintString(context.getState(), 10, 70, 0, 0, 0, 24, "违法停车告知单", "gb2312");
        //text
        context.getObject().CPCL_PrintString(context.getState(), 10, 110, 0, 0, 0, 24,"NO.124321321312321", "gb2312");
        //last text
        context.getObject().CPCL_PrintString(context.getState(), 10, 134, 0, 0, 0, 24, "车辆类型：小型客车", "gb2312");
        context.getObject().CPCL_PrintString(context.getState(), 10, 158, 0, 0, 0, 24, "车辆号牌：京P123232", "gb2312");
        context.getObject().CPCL_PrintString(context.getState(), 10, 198, 0, 0, 0, 24, "号牌颜色：蓝色", "gb2312");
        context.getObject().CPCL_PrintString(context.getState(), 10, 222, 0, 0, 0, 24, "违停时间:2016年12月12日12时12分", "gb2312");
        //last text
        context.getObject().CPCL_PrintString(context.getState(), 10, 246, 0, 0, 0, 24, "违停地点：景北路口至湖光路口中街北段", "gb2312");
        context.getObject().CPCL_PrintString(context.getState(), 10, 280, 0, 0, 0, 24, "       上述时间、地点该机动车未在道路停车泊位或停车场内停放，根据《北京市实施〈中华人民共和国道路交通安全法〉办法》第七十八条第四款的规定，已对以上事实作了图像记录。此告知单及图像记录，将提供给——————（所属部门名）审核。", "gb2312");
        context.getObject().CPCL_PrintString(context.getState(), 200, 310, 0, 0, 0, 24, "联系电话：123456780900", "gb2312");
        context.getObject().CPCL_PrintString(context.getState(), 200, 334, 0, 0, 0, 24, "协警姓名：李宇春", "gb2312");
        context.getObject().CPCL_PrintString(context.getState(), 200, 358, 0, 0, 0, 24, "2016年12月12日", "gb2312");

        //context.getObject().CPCL_Print2DBarcode(context.getState(),BarcodeType.BT_CODE128.getValue(), 0, 0, 2, 2, 4, "LA,dt124hkl1com1p1q1on1XJO1607150018", "gb2312");//http://dt.24hkl.com/p/q?on=XJO1607150018

        context.getObject().CON_PageEnd(context.getState(),
                context.getPrintway());
    }
    private void getDevice(){
        ArrayList<String> getbtNM = (ArrayList<String>) context.getObject()
                .CON_GetWirelessDevices(0);
        // 对获得的蓝牙地址和名称进行拆分以逗号进行拆分
        btName.clear();
        btId.clear();
        if(getbtNM==null||getbtNM.size()==0){
            Toast.makeText(this,"未找到匹配设备，请检查手机及设备蓝牙状况",Toast.LENGTH_SHORT).show();
        }
        if(getbtNM==null){
            getbtNM = new ArrayList<>();
        }
        for (int i = 0; i < getbtNM.size(); i++) {
            btName.add(getbtNM.get(i).split(",")[0]);
            btId.add(getbtNM.get(i).split(",")[1].substring(0,
                    17));
        }
        mHandler.sendEmptyMessage(1);
    }
    private void initView() {
        loading = new ComDialog.Builder(this).setDialogView(R.layout.loading_dialog).setStyle(R.style.ShareDialog).setEndDuration(200).setEndTechnique(Techniques.FadeOutDown).setGravity(Gravity.CENTER).setIsCancelable(false).setStartDuration(200).setStartTechnique(Techniques.BounceIn).build();
        ((TextView)loading.findViewById(R.id.show_tv)).setText("正在连接打印机");
        spinner = (Spinner)findViewById(R.id.spinner);
        printName = new ArrayList<String>();
        printName = (ArrayList<String>) context.getObject()
                .CON_GetSupportPrinters();
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, printName);
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        this.spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PrintActivity.this, btName.get(position)+btId.get(position),Toast.LENGTH_SHORT).show();
                btMaxId = btId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.back:
               this.finish();
               break;
           case R.id.print:
//               Intent intent = new Intent(this,TakePictureActivity.class);
//               startActivity(intent);
               if(mBconnect){
                   printLable();
                   return;
               }
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       connect(printName.get(0),btMaxId);
                   }
               }).start();
               loading.show();
               break;
       }
    }
    private IntentFilter makeFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return filter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(mReceiver,makeFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mReceiver);
    }

    private String bluetoothStatus;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Toast.makeText(PrintActivity.this,"正在开启蓝牙",Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothAdapter.STATE_ON:
                            bluetoothStatus="on";
                            Toast.makeText(PrintActivity.this,"蓝牙已开启",Toast.LENGTH_SHORT).show();
                            getDevice();
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.e("TAG", "STATE_TURNING_OFF");
                            Toast.makeText(PrintActivity.this,"蓝牙正在关闭",Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            bluetoothStatus="off";
                            Log.e("TAG", "STATE_OFF");
                            Toast.makeText(PrintActivity.this,"蓝牙已关闭",Toast.LENGTH_SHORT).show();
                            btName.clear();
                            btId.clear();
                            mHandler.sendEmptyMessage(1);
                            break;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        disconnect();
        super.onDestroy();
    }

    public void disconnect(){//断开设备
       if (mBconnect) {
           context.getObject().CON_CloseDevices(context.getState());
           mHandler.sendEmptyMessage(2);
           mBconnect = false;
       }
   }

    public void connect(String PrintName,String port) {
            state = context.getObject().CON_ConnectDevices(PrintName, port, 200);
            if (state > 0) {
                mBconnect = true;
                context.setState(state);
                context.setName(PrintName);
                context.setPrintway(0);
                mHandler.sendEmptyMessage(3);
//                startActivity(intent);
            } else {
//                Toast.makeText(PrintActivity.this, "链接打印机失败",
//                        Toast.LENGTH_SHORT).show();
                mBconnect = false;
//                con.setText(R.string.button_btcon);// "连接"
                mHandler.sendEmptyMessage(4);

            }
        }
}
