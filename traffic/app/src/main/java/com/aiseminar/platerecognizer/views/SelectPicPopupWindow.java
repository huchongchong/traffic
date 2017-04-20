package com.aiseminar.platerecognizer.views;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiseminar.platerecognizer.R;


public class SelectPicPopupWindow extends PopupWindow {
	    private RelativeLayout btn_One, btn_Two, btn_Three;
	    public TextView tv_One, tv_Two, tv_Three;
	    private View mMenuView;  

	    public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick, String strOne, String strTwo, String strThree, String strAdd1) {
	        super(context);  
	        LayoutInflater inflater = (LayoutInflater) context  
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	        mMenuView = inflater.inflate(R.layout.alert_dialog, null);
	        btn_One = (RelativeLayout) mMenuView.findViewById(R.id.btn_one);
	        btn_Two = (RelativeLayout) mMenuView.findViewById(R.id.btn_two);  
	        btn_Three = (RelativeLayout) mMenuView.findViewById(R.id.btn_three); 
//	        btn_add1 = (RelativeLayout) mMenuView.findViewById(R.id.btn_add1);
	        tv_One = (TextView) mMenuView.findViewById(R.id.tv_one);
	        tv_Two = (TextView) mMenuView.findViewById(R.id.tv_two);
	        tv_Three = (TextView) mMenuView.findViewById(R.id.tv_three);
	        //tv_add1 = (TextView) mMenuView.findViewById(R.id.tv_add1);
//	        addline1 = mMenuView.findViewById(R.id.lineadd1);
//	        if(strAdd1!=null){
//	        	btn_add1.setVisibility(View.VISIBLE);
//	        	addline1.setVisibility(View.VISIBLE);
//	        	tv_add1.setText(strAdd1);
//	        }else{
//	        	btn_add1.setVisibility(View.GONE);
//	        	addline1.setVisibility(View.GONE);
//	        }
	        tv_One.setText(strOne);
	        tv_Two.setText(strTwo);
	        tv_Three.setText(strThree);
	        //取消按钮  
	        btn_Three.setOnClickListener(new OnClickListener() {  
	  
	            public void onClick(View v) {  
	                //销毁弹出框  
	                dismiss();
				}
	        });  
	        //设置按钮监听  
	        btn_One.setOnClickListener(itemsOnClick); 
	        btn_Two.setOnClickListener(itemsOnClick);
	        //btn_Three.setOnClickListener(itemsOnClick);
	      //  btn_add1.setOnClickListener(itemsOnClick);
	        //设置SelectPicPopupWindow的View  
	        this.setContentView(mMenuView);  
	        //设置SelectPicPopupWindow弹出窗体的宽  
	        this.setWidth(LayoutParams.FILL_PARENT);  
	        //设置SelectPicPopupWindow弹出窗体的高  
	        this.setHeight(LayoutParams.WRAP_CONTENT);  
	        //设置SelectPicPopupWindow弹出窗体可点击  
	        this.setFocusable(true);  
	        //设置SelectPicPopupWindow弹出窗体动画效果  
	        this.setAnimationStyle(R.style.AnimBottom);  
	        //实例化一个ColorDrawable颜色为半透明  
	        ColorDrawable dw = new ColorDrawable(0xb0000000);
	        //设置SelectPicPopupWindow弹出窗体的背景  
	        this.setBackgroundDrawable(dw);  
	        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
	        mMenuView.setOnTouchListener(new OnTouchListener() {  
	              
	            public boolean onTouch(View v, MotionEvent event) {  
	                  
	                int height = mMenuView.findViewById(R.id.pop_layout).getTop();  
	                int y=(int) event.getY();  
	                if(event.getAction()==MotionEvent.ACTION_UP){  
	                    if(y<height){  
	                        dismiss();  
	                    }  
	                }                 
	                return true;  
	            }  
	        });  
	  
	    }  
	  
}
