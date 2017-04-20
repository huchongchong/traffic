package com.aiseminar.platerecognizer.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.fragments.LawFrag;
import com.aiseminar.platerecognizer.fragments.MapFrag;
import com.aiseminar.platerecognizer.fragments.QueryFrag;
import com.aiseminar.platerecognizer.fragments.ReportFrag;
import com.aiseminar.platerecognizer.fragments.TaskFrag;
import com.aiseminar.platerecognizer.model.Task;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView TvtopTitle;
    private FrameLayout content;
    private LinearLayout task;
    private LinearLayout map;
    private LinearLayout law;
    private LinearLayout query;
    private LinearLayout report;
    private MapFrag mapFrag;
    private LawFrag lawFrag;
    private QueryFrag queryFrag;
    private ReportFrag reportFrag;
    private TaskFrag taskFrag;
    private FragmentTransaction mTransaction;
    private int curIndex;
    private LinearLayout curIndexView;
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TvtopTitle.setText(msg.obj.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        getPersimmions();
    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    private void initView() {
        TvtopTitle = (TextView)findViewById(R.id.topTitle);
        content = (FrameLayout)findViewById(R.id.content);
        task = (LinearLayout)findViewById(R.id.task);
        map = (LinearLayout)findViewById(R.id.map);
        law = (LinearLayout)findViewById(R.id.law);
        query = (LinearLayout)findViewById(R.id.query);
        report = (LinearLayout)findViewById(R.id.report);
        task.setOnClickListener(this);
        map.setOnClickListener(this);
        law.setOnClickListener(this);
        query.setOnClickListener(this);
        report.setOnClickListener(this);
        task.getChildAt(0).setBackgroundResource(R.mipmap.task_hl);
        ((TextView)task.getChildAt(1)).setTextColor(Color.parseColor("#55A2F0"));
        curIndex = 0;
        curIndexView = task;
        mapFrag = new MapFrag();
        lawFrag = new LawFrag();
        queryFrag = new QueryFrag();
        reportFrag = new ReportFrag();
        taskFrag = new TaskFrag();
        mTransaction = getFragmentManager().beginTransaction();
        mTransaction.replace(R.id.content,taskFrag).commit();
        List list = new ArrayList<Task>();
        Task task = new Task();
        task.notice = new ArrayList();
        task.notice.add("11111111111");
        task.notice.add("22222222222");
        task.notice.add("33333333333");
        task.notice.add("44444444444");
        task.type = 1;
        list.add(task);

        Task task2 = new Task();
        task2.type = 2;
        list.add(task2);
        for(int i=0;i<4;i++){
            Task task3 = new Task();
            task3.type = 3;
            task3.content = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
            list.add(task3);
        }
        Task task4 = new Task();
        task4.type = 2;
        list.add(task4);
        for(int i=0;i<4;i++){
            Task task5 = new Task();
            task5.type = 3;
            task5.content = "qqqqqqqqqqqq";
            list.add(task5);
        }
        taskFrag.setData(list);
        List list2 = new ArrayList<Task>();
        for(int i=0;i<10;i++) {
            Task t = new Task();
            list2.add(t);
        }
        queryFrag.setData(list2);
    }
    void setAnmitor(int index){
        if(index>curIndex){
            mTransaction.setCustomAnimations(
                    R.animator.fragment_slide_enter_right,
                    R.animator.fragment_slide_exit_right);
        }else{
            mTransaction.setCustomAnimations(
                    R.animator.fragment_slide_enter_left,
                    R.animator.fragment_slide_exit_left);
        }
    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.task:
               if(curIndex==0){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(0);
               mTransaction.replace(R.id.content,taskFrag).commit();
               TvtopTitle.setText("任务");
               changeSelect(task.getChildAt(0), (TextView) task.getChildAt(1),0);
               curIndex = 0;
               curIndexView = task;
               break;
           case R.id.map:
               if(curIndex==1){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(1);
               mTransaction.replace(R.id.content,mapFrag).commit();
               TvtopTitle.setText("导航");
               changeSelect(map.getChildAt(0), (TextView) map.getChildAt(1),1);
               curIndex = 1;
               curIndexView = map;

           break;
           case R.id.query:
               if(curIndex==3){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(3);
               mTransaction.replace(R.id.content,queryFrag).commit();
               TvtopTitle.setText("历史记录");
               changeSelect(query.getChildAt(0), (TextView) query.getChildAt(1),3);
               curIndex = 3;
               curIndexView = query;
           break;
           case R.id.law:
               if(curIndex==2){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(2);
               mTransaction.replace(R.id.content,lawFrag).commit();
               TvtopTitle.setText("一键识别");
               changeSelect(law.getChildAt(0), (TextView) law.getChildAt(1),2);
               curIndex = 2;
               curIndexView = law;
           break;
           case R.id.report:
               if(curIndex==4){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(4);
               mTransaction.replace(R.id.content,reportFrag).commit();
               TvtopTitle.setText("巡查报告");
               changeSelect(report.getChildAt(0), (TextView) report.getChildAt(1),4);
               curIndex = 4;
               curIndexView = report;
           break;
       }
    }
   public void setTitle(String str){
       Message message = Message.obtain();
       message.what = 1;
       message.obj = str;
       mHandler.sendMessage(message);
    }
    void changeSelect(View v,TextView v2,int index){
        switch (index){
            case 0:
                v.setBackgroundResource(R.mipmap.task_hl);
            break;
            case 1:
                v.setBackgroundResource(R.mipmap.location_hl);
            break;
            case 2:
                v.setBackgroundResource(R.mipmap.camera_hl);
            break;
            case 3:
                v.setBackgroundResource(R.mipmap.message_hl);
            break;
            case 4:
                v.setBackgroundResource(R.mipmap.edit_hl);
            break;
        }
        v2.setTextColor(Color.parseColor("#55A2F0"));
        switch (curIndex){
            case 0:
                curIndexView.getChildAt(0).setBackgroundResource(R.mipmap.task_nor);
            break;
            case 1:
                curIndexView.getChildAt(0).setBackgroundResource(R.mipmap.location_nor);
            break;
            case 2:
                curIndexView.getChildAt(0).setBackgroundResource(R.mipmap.camera_nor);
            break;
            case 3:
                curIndexView.getChildAt(0).setBackgroundResource(R.mipmap.message_nor);
            break;
            case 4:
                curIndexView.getChildAt(0).setBackgroundResource(R.mipmap.edit_nor);
            break;
        }
        ((TextView)curIndexView.getChildAt(1)).setTextColor(getResources().getColor(R.color.nor));
    }
}
