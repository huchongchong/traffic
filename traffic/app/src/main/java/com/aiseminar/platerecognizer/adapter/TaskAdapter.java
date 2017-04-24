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

import com.aiseminar.platerecognizer.activity.MessageActivity;
import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.model.Task;
import com.aiseminar.platerecognizer.views.ExpandableView;

import java.util.List;

/**
 *
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements View.OnClickListener {
    private final Context con;
    private final List<Task> datas;
    private int showIndex;
    private Animation an;

    public TaskAdapter(List<Task> datas, Context con) {
        this.datas = datas;
        this.con = con;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        if(viewType==1){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice, viewGroup, false);
        }else if(viewType==2){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task_tittle, viewGroup, false);
        }else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
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
            viewHolder.type = getItemViewType(position);
          if(position==0){
              showIndex = 0;
              viewHolder.noticeTv.removeAllViews();
              for(int i=0;i<datas.get(0).notice.size();i++){
                  TextView textView = new TextView(con);
                  textView.setTextColor(Color.BLACK);
                  textView.setText(datas.get(0).notice.get(i));
                  textView.setTextSize(22);
                  viewHolder.noticeTv.addView(textView);
              }
              viewHolder.noticeTv.setClickable(true);
              viewHolder.noticeTv.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(con, MessageActivity.class);
                      con.startActivity(intent);
                  }
              });
              viewHolder.notice.startAnimation(AnimationUtils.loadAnimation(con, R.anim.notice));
              an = AnimationUtils.loadAnimation(con, R.anim.scroll);
              an.setFillAfter(true);
              an.setFillEnabled(true);
              an.setFillBefore(true);
              an.setDuration(datas.get(0).notice.size()*2000);
              an.setAnimationListener(new Animation.AnimationListener() {
                  @Override
                  public void onAnimationStart(Animation animation) {
                  }

                  @Override
                  public void onAnimationEnd(Animation animation) {
                      animation.start();
                  }

                  @Override
                  public void onAnimationRepeat(Animation animation) {
                  }
              });
             // viewHolder.noticeTv.setText("111111"+"\n"+"22222222");
              viewHolder.noticeTv.startAnimation(an);
          }
            if(viewHolder.type==3){
                viewHolder.content.setText("描述:"+datas.get(position).content);
                viewHolder.num.setText(datas.get(position).num);
                viewHolder.locationDes.setText(datas.get(position).locationDes);
                viewHolder.time.setText("时间:"+datas.get(position).time);
                viewHolder.botton.setOnClickListener(this);
                viewHolder.botton.setTag(datas.get(position));
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
            if(getItemViewType(position)==2){
                viewHolder.title.setText(datas.get(position).title);
            }
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
        if(holder.type==1){
            holder.notice.clearAnimation();
            holder.noticeTv.clearAnimation();
        }
        super.onViewRecycled(holder);
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
       if(infoClick!=null&&v.getTag()!=null&&v.getTag() instanceof Task){
           infoClick.onInfoClick((Task) v.getTag());
       }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView notice;
        public ImageView oran;
        public LinearLayout noticeTv;
        public int type;
        public TextView content,botton,title,num,locationDes,time;
        public ExpandableView expandableView;
        public ViewHolder(View view,int type) {
            super(view);
            if(type==1){
                notice = (ImageView) view.findViewById(R.id.icon);
                noticeTv = (LinearLayout) view.findViewById(R.id.notice);
            }
            if(type==3){
               expandableView = (ExpandableView) view;
                oran =(ImageView) view.findViewById(R.id.oran);
                content = (TextView)view.findViewById(R.id.content);
                botton = (TextView)view.findViewById(R.id.botton);
                num = (TextView)view.findViewById(R.id.num);
                locationDes = (TextView)view.findViewById(R.id.location_des);
                time = (TextView)view.findViewById(R.id.time);
            }
            if(type==2){
                title = (TextView)view.findViewById(R.id.title);
            }
        }
    }
    public InfoClick infoClick;
    public void setInfoClick(InfoClick infoClick){
        this.infoClick = infoClick;
    }
    public  interface InfoClick{
        void onInfoClick(Task task);
    }
}
