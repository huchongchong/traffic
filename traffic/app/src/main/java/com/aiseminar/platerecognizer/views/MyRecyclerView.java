package com.aiseminar.platerecognizer.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.aiseminar.platerecognizer.model.LoadMore;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyRecyclerView extends RecyclerView{
    private LinearLayoutManager mLayoutManager;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    public LoadMore LoadMore;
    private int lastVisibleItemPosition;

    public MyRecyclerView(Context context) {
        super(context);
    }



    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if(layout instanceof LinearLayoutManager) {
            this.mLayoutManager = (LinearLayoutManager) layout;
        }
        super.setLayoutManager(layout);
    }
    public void setLoadMore(LoadMore LoadMore){
        this.LoadMore = LoadMore;
    }
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        Log.e("onScrollStateChanged","RecyclerView.SCROLL_STATE_DRAGGING:"+RecyclerView.SCROLL_STATE_DRAGGING);
        Log.e("onScrollStateChanged","RecyclerView.SCROLL_STATE_IDLE:"+RecyclerView.SCROLL_STATE_IDLE);
        Log.e("onScrollStateChanged","RecyclerView.SCROLL_STATE_SETTLING:"+RecyclerView.SCROLL_STATE_SETTLING);
        Log.e("onScrollStateChanged","onScrollStateChanged:"+state);
        if ((visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
           if(!LoadMore.isLoadding&&!LoadMore.isAllLoad) {
               LoadMore.loadMoreLinstener.onLoadMoreLinstener(LoadMore);
           }
        }
    }
    public void setLoadState(boolean isLoadding,boolean isAllLoad,int page){
        this.LoadMore.isAllLoad = isAllLoad;
        this.LoadMore.isLoadding = isLoadding;
        this.LoadMore.curPage = page;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.e("onScrollChanged","onScrollChanged");
        super.onScrollChanged(l, t, oldl, oldt);
    }
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        Log.e("dy",dy+"");
        visibleItemCount = this.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
        firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
        if ((totalItemCount - visibleItemCount) <= firstVisibleItem) {
            Log.e("dy",dy+"buttom");
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        Log.e("onOverScrolled","onOverScrolled");
    }
}
