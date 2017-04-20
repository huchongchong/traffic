package com.aiseminar.platerecognizer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.adapter.MassageAdapter;
import com.aiseminar.platerecognizer.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }
    private void initView() {
        List list = new ArrayList<Task>();
            Task task3 = new Task();
            task3.type = 3;
            task3.content = "12:00  北四环学院路路段出现大车侧翻北四环学院路路段出现大车侧翻北四环学院路路段出现大车侧翻北四环学院路路段出现大车侧翻";
            list.add(task3);
            Task task1 = new Task();
            task1.type = 3;
            task1.content = "08:00  广渠路双井桥附近六车追尾";
            list.add(task1);
            Task task2 = new Task();
            task2.type = 3;
            task2.content = "12:00 京藏高速健翔桥东路段拥堵";
            list.add(task2);

        recyclerView = (RecyclerView)findViewById(R.id.task_content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MassageAdapter taskAdapter = new MassageAdapter(list,this);
        recyclerView.setAdapter(taskAdapter);
    }
}
