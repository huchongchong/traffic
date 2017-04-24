package com.aiseminar.platerecognizer.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.views.ComDialog;
import com.daimajia.androidanimations.library.Techniques;


public class InformationConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private ComDialog modifyDialog,selectDialog;
    private TextView location,car_type,car_num,car_num_color;
    private String[] strColor = new String[]{"蓝","黑","黄"};
    private String[] strType = new String[]{"A级车","B级车","C级车"};
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    location.setText(msg.obj.toString());
                    break;
                case 2:
                    car_num.setText(msg.obj.toString());
                    break;
                case 3:
                    initSelectDialog(strType,31);
                    break;
                case 31:
                    selectDialog.animateDismiss();
                    car_type.setText(msg.obj.toString());
                    break;
                case 4:
                    initSelectDialog(strColor,41);
                    break;
                case 41:
                    selectDialog.animateDismiss();
                    car_num_color.setText(msg.obj.toString());
                    break;
            }
        }
    };
    private LinearLayout selectDialogLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_confirm);
        initView();
    }

    private void initView() {
        location = (TextView)findViewById(R.id.location);
        car_type = (TextView)findViewById(R.id.car_type);
        car_num = (TextView)findViewById(R.id.car_num);
        car_num_color = (TextView)findViewById(R.id.car_num_color);
        modifyDialog = new ComDialog.Builder(this).setDialogView(R.layout.diglog_input).setStyle(R.style.ShareDialog).setEndDuration(200).setEndTechnique(Techniques.FadeOutDown).setGravity(Gravity.CENTER).setIsCancelable(false).setViewClickLinstener(R.id.ok,this).setViewClickLinstener(R.id.cancel,this).setStartDuration(200).setStartTechnique(Techniques.BounceIn).build();
        selectDialog = new ComDialog.Builder(this).setDialogView(R.layout.diglog_select).setStyle(R.style.ShareDialog).setEndDuration(200).setEndTechnique(Techniques.FadeOutDown).setGravity(Gravity.CENTER).setIsCancelable(false).setStartDuration(200).setStartTechnique(Techniques.BounceIn).build();
        selectDialogLl = (LinearLayout)selectDialog.findViewById(R.id.ll);
    }
    void initSelectDialog(final String[] strs, final int what){
        selectDialogLl.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i=0;i<strs.length;i++){
//            final TextView textView = (TextView) inflater.inflate(R.layout.item_text,null);
            TextView textView = new TextView(this);
            textView.setTextColor(getResources().getColor(R.color.black_overlay));
            textView.setTextSize(20);
            textView.setClickable(true);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.topMargin = 20;
            params.bottomMargin = 20;
            textView.setLayoutParams(params);
            textView.setText(strs[i]);
            selectDialogLl.addView(textView);
            if(i!=strs.length-1){
                View v = new View(this);
                v.setBackgroundColor(getResources().getColor(R.color.civ_border_color));
                LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                pm.leftMargin=10;
                pm.rightMargin = 10;
                v.setLayoutParams(pm);
                selectDialogLl.addView(v);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    Message msg = Message.obtain();
                    msg.what = what;
                    msg.obj = tv.getText();
                    mHandler.handleMessage(msg);
                }
            });
        }
        selectDialog.show();
    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.location:
           case R.id.edit_icon2:
               modifyDialog.setShowType(1);
               modifyDialog.show();
               break;
           case R.id.car_type:
                mHandler.sendEmptyMessage(3);
           case R.id.edit_icon3:
               break;
           case R.id.car_num:
           case R.id.edit_icon4:
               modifyDialog.setShowType(2);
               modifyDialog.show();
               break;
           case R.id.car_num_color:
           case R.id.edit_icon5:
               mHandler.sendEmptyMessage(4);
               break;
           case R.id.ok:
               EditText editText = (EditText) modifyDialog.findViewById(R.id.input);
               if(editText!=null){
                   if(editText.getText()!=null&&!TextUtils.isEmpty(editText.getText().toString())){
                       modifyDialog.animateDismiss();
                       Message msg = Message.obtain();
                       msg.what = modifyDialog.showType;
                       msg.obj = editText.getText().toString();
                       mHandler.sendMessage(msg);
                   }else{
                       Toast.makeText(InformationConfirmActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                   }

               }
               editText.setText("");
               break;
           case R.id.cancel:
               modifyDialog.animateDismiss();
               break;
           case R.id.back:
               this.finish();
               break;
           case R.id.punishment:
               Intent intent = new Intent(this,PrintActivity.class);
               startActivity(intent);
           break;
           case R.id.warn:
               Intent intent2 = new Intent(this,PrintActivity.class);
               startActivity(intent2);
           break;
           case R.id.record:
               Intent intent3 = new Intent(this,PrintActivity.class);
               startActivity(intent3);
           break;
       }
    }
}
