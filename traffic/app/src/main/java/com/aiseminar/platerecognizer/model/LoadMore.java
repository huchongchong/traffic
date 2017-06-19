package com.aiseminar.platerecognizer.model;

/**
 * Created by Administrator on 2017/5/3.
 */

public class LoadMore {
    public boolean isLoadding;
    public int curPage;
    public boolean isAllLoad;
    public LoadMoreLinstener loadMoreLinstener;

    public LoadMore(boolean isLoadding, int curPage, boolean isAllLoad, LoadMoreLinstener loadMoreLinstener) {
        this.isLoadding = isLoadding;
        this.curPage = curPage;
        this.isAllLoad = isAllLoad;
        this.loadMoreLinstener = loadMoreLinstener;
    }

    public boolean isLoadding() {
        return isLoadding;
    }

    public void setLoadding(boolean loadding) {
        isLoadding = loadding;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public boolean isAllLoad() {
        return isAllLoad;
    }

    public void setAllLoad(boolean allLoad) {
        isAllLoad = allLoad;
    }

    public LoadMoreLinstener getLoadMoreLinstener() {
        return loadMoreLinstener;
    }

    public void setLoadMoreLinstener(LoadMoreLinstener loadMoreLinstener) {
        this.loadMoreLinstener = loadMoreLinstener;
    }

    public interface LoadMoreLinstener{
       void onLoadMoreLinstener(LoadMore loadMore);
    }
}
