package com.aiseminar.platerecognizer;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiseminar.platerecognizer.fragments.LawFrag;
import com.aiseminar.platerecognizer.fragments.MapFrag;
import com.aiseminar.platerecognizer.fragments.QueryFrag;
import com.aiseminar.platerecognizer.fragments.ReportFrag;
import com.aiseminar.platerecognizer.fragments.TaskFrag;
import com.aiseminar.platerecognizer.model.Task;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
        mapFrag = new MapFrag();
        lawFrag = new LawFrag();
        queryFrag = new QueryFrag();
        reportFrag = new ReportFrag();
        taskFrag = new TaskFrag();
        mTransaction = getFragmentManager().beginTransaction();
        mTransaction.replace(R.id.content,taskFrag).commit();
        curIndex = 0;
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
               curIndex = 0;
               break;
           case R.id.map:
               if(curIndex==1){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(1);
               mTransaction.replace(R.id.content,mapFrag).commit();
               TvtopTitle.setText("导航");
               curIndex = 1;

           break;
           case R.id.query:
               if(curIndex==3){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(3);
               mTransaction.replace(R.id.content,queryFrag).commit();
               TvtopTitle.setText("历史记录");
               curIndex = 3;
           break;
           case R.id.law:
               if(curIndex==2){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(2);
               mTransaction.replace(R.id.content,lawFrag).commit();
               TvtopTitle.setText("执法");
               curIndex = 2;
           break;
           case R.id.report:
               if(curIndex==4){
                   return;
               }
               mTransaction = getFragmentManager().beginTransaction();
               setAnmitor(4);
               mTransaction.replace(R.id.content,reportFrag).commit();
               TvtopTitle.setText("上报");
               curIndex = 4;
           break;
       }
    }
}
