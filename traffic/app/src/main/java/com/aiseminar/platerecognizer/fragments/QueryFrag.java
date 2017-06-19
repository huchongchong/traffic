package com.aiseminar.platerecognizer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.adapter.HistoryAdapter;
import com.aiseminar.platerecognizer.adapter.TaskAdapter;
import com.aiseminar.platerecognizer.model.LoadMore;
import com.aiseminar.platerecognizer.model.Task;
import com.aiseminar.platerecognizer.views.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class QueryFrag extends Fragment implements SwipeRefreshLayout.OnRefreshListener,LoadMore.LoadMoreLinstener {
    private MyRecyclerView recyclerView;
    private List<Task> list =new ArrayList();
    private HistoryAdapter taskAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMore loadMore;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                recyclerView.setLoadState(false,false,recyclerView.LoadMore.curPage);
                List list1 = new ArrayList();
                list1.add(list.get(0));
                list1.add(list.get(1));
                list1.add(list.get(2));
                taskAdapter.addDatas(list1);
            }else if(msg.what==2){
                recyclerView.setLoadState(false,true,recyclerView.LoadMore.curPage);
                taskAdapter.notifyFootState(true);
            }
        }
    };
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
        recyclerView = (MyRecyclerView)view.findViewById(R.id.task_content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        loadMore = new LoadMore(false,1,false,this);
        recyclerView.setLoadMore(loadMore);
        taskAdapter = new HistoryAdapter(list,getActivity());
        recyclerView.setAdapter(taskAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.logbtntext
        );
        swipeRefreshLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreLinstener(LoadMore loadMore) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLoadState(true,false,recyclerView.LoadMore.curPage+1);
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(recyclerView.LoadMore.curPage>=6){
                    mHandler.sendEmptyMessage(2);
                }else {
                    mHandler.sendEmptyMessage(1);
                }
            }
        }).start();
    }
}
