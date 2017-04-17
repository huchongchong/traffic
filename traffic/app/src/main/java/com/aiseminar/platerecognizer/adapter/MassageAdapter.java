package com.aiseminar.platerecognizer.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiseminar.platerecognizer.MessageActivity;
import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.model.Task;
import com.aiseminar.platerecognizer.views.ExpandableView;

import java.util.List;

/**
 *
 */
public class MassageAdapter extends RecyclerView.Adapter<MassageAdapter.ViewHolder> implements View.OnClickListener {
    private final Context con;
    private final List<Task> datas;

    public MassageAdapter(List<Task> datas, Context con) {
        this.datas = datas;
        this.con = con;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
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
            viewHolder.content.setText(datas.get(position).content);
        }
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
        public TextView content;
        public ViewHolder(View view,int type) {
            super(view);
                content = (TextView)view.findViewById(R.id.content);
            }
        }
}
