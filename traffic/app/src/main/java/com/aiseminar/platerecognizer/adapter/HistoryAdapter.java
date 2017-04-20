package com.aiseminar.platerecognizer.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 *
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements View.OnClickListener {
    private final Context con;
    public List<Task> datas;

    public HistoryAdapter(List<Task> datas, Context con) {
        this.datas = datas;
        this.con = con;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
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
              }else{
                  viewHolder.typeIcon.setImageResource(R.mipmap.arrow_l_r);
              }
                viewHolder.expandableView.setListener(new ExpandableView.OnViewExpandStateChangeListener() {
                    @Override
                    public void onViewExpandStateChanged(View view, boolean isExpanded) {
                        if(!isExpanded){
                            ObjectAnimator anim = ObjectAnimator.ofFloat(viewHolder.oran, "rotation", 0f, 180f);
                            anim.setDuration(150);
                            anim.start();
                        }else{
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
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    void StartAnmition(View v) {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(con, R.anim.notice);
        v.setAnimation(animationSet);
        animationSet.start();
    }
    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {

    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView oran,typeIcon;
        public ExpandableView expandableView;
        public TextView carNum,time,location,location_ex,cause,phone;
        public ViewHolder(View view,int type) {
            super(view);
                expandableView = (ExpandableView) view;
                oran =(ImageView) view.findViewById(R.id.oran);
                typeIcon =(ImageView) view.findViewById(R.id.type_icon);
                carNum = (TextView)view.findViewById(R.id.car_num);
                time = (TextView)view.findViewById(R.id.time);
                location = (TextView)view.findViewById(R.id.location);
                cause = (TextView)view.findViewById(R.id.cause);
                location_ex = (TextView)view.findViewById(R.id.location_ex);
                phone = (TextView)view.findViewById(R.id.phone);
        }
    }
}
