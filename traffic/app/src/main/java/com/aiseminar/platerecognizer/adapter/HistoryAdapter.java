package com.aiseminar.platerecognizer.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.model.Task;
import com.aiseminar.platerecognizer.views.ExpandableView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements View.OnClickListener {
    private final Context con;
    public List<Task> datas;
    private boolean isAllLoad = false;

    public HistoryAdapter(List<Task> datas, Context con) {
        if(datas==null){
            datas = new ArrayList<Task>();
        }
        this.datas = datas;
        this.con = con;
    }
    public void addDatas(List<Task> adddatas){
        if(adddatas==null){
            return;
        }
        int position = datas.size()-1;
        datas.addAll(adddatas);
        notifyItemRangeChanged(position,adddatas.size());
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        if(viewType!=10) {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_loadmore, viewGroup, false);
        }
        return new ViewHolder(view,viewType);
    }

    /**
     * @param viewHolder
     * @param position
     */
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (datas != null && datas.size() > position) {
              if(position==0){
                  viewHolder.typeIcon.setImageResource(R.mipmap.warn);
              }else if(position==1){
                  viewHolder.typeIcon.setImageResource(R.mipmap.ok);
              }else if(position!=datas.size()){
                  viewHolder.typeIcon.setImageResource(R.mipmap.arrow_l_r);
              }
            Log.e("position","   "+position);
            if(position!=datas.size()) {
                viewHolder.expandableView.setListener(new ExpandableView.OnViewExpandStateChangeListener() {
                    @Override
                    public void onViewExpandStateChanged(View view, boolean isExpanded) {
                        if (!isExpanded) {
                            ObjectAnimator anim = ObjectAnimator.ofFloat(viewHolder.oran, "rotation", 0f, 180f);
                            anim.setDuration(150);
                            anim.start();
                        } else {
                            ObjectAnimator anim = ObjectAnimator.ofFloat(viewHolder.oran, "rotation", -180f, 0f);
                            anim.setDuration(150);
                            anim.start();
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }else{
            if(isAllLoad){
                viewHolder.loaddingIcon.setVisibility(View.INVISIBLE);
                viewHolder.loadText.setText("已经加载全部");
            }else {
                StartAnmition(viewHolder.loaddingIcon);
            }
        }
    }
    @Override
    public void onViewRecycled(HistoryAdapter.ViewHolder holder) {
        if(holder.type==10){
           // holder.loaddingIcon.clearAnimation();
        }
        super.onViewRecycled(holder);
    }
    @Override
    public int getItemViewType(int position) {
        if(position==datas.size()){
            return 10;
        }
        return datas.get(position).getType();
    }

    void StartAnmition(View v) {
        Log.e("anmi","start");
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(con, R.anim.rount);
        v.setAnimation(animationSet);
        animationSet.start();
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size()+1;
    }

    @Override
    public void onClick(View v) {

    }

    public void notifyFootState(boolean b) {
        if(b){
            this.isAllLoad = b;
            notifyItemChanged(datas.size());
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView oran,typeIcon,loaddingIcon;
        public ExpandableView expandableView;
        public TextView carNum,time,location,location_ex,cause,phone,loadText;
        public int type;
        public ViewHolder(View view,int type) {
            super(view);
            this.type = type;
            if(view instanceof ExpandableView) {
                expandableView = (ExpandableView) view;
                oran = (ImageView) view.findViewById(R.id.oran);
                typeIcon = (ImageView) view.findViewById(R.id.type_icon);
                carNum = (TextView) view.findViewById(R.id.car_num);
                time = (TextView) view.findViewById(R.id.time);
                location = (TextView) view.findViewById(R.id.location);
                cause = (TextView) view.findViewById(R.id.cause);
                location_ex = (TextView) view.findViewById(R.id.location_ex);
                phone = (TextView) view.findViewById(R.id.phone);
            }else{
                loaddingIcon = (ImageView) view.findViewById(R.id.loadding);
                loadText = (TextView) view.findViewById(R.id.title);
            }
        }
    }
}
