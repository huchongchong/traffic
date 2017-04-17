package com.aiseminar.platerecognizer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.adapter.HistoryAdapter;
import com.aiseminar.platerecognizer.adapter.TaskAdapter;
import com.aiseminar.platerecognizer.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class QueryFrag extends Fragment {
    private RecyclerView recyclerView;
    private List<Task> list =new ArrayList();
    private HistoryAdapter taskAdapter;

    public QueryFrag() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_query,null);
        findView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    protected void findView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.task_content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new HistoryAdapter(list,getActivity());
        recyclerView.setAdapter(taskAdapter);
    }
    public void setData(List<Task> list){
        this.list = list;
        if(taskAdapter!=null){
            taskAdapter.datas = list;
            taskAdapter.notifyDataSetChanged();
        }
    }
    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
