package com.aiseminar.platerecognizer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.fragments.ReportFrag;
import com.aiseminar.platerecognizer.util.BitmapUtil;
import com.aiseminar.platerecognizer.util.PhotosUtil;
import com.aiseminar.platerecognizer.views.SelectPicPopupWindow;

import java.io.IOException;


public class TakePictureActivity extends AppCompatActivity implements View.OnClickListener ,PhotosUtil.OnPhotoReturn{
    private ImageView clickView;
    private PhotosUtil photosUtil;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };
    private SelectPicPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takepicture);
        initView();
    }

    private void initView() {
        menuWindow = new SelectPicPopupWindow(this,
                this, "从相册选择", "拍照", "取消", null);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        photosUtil.DistributeIntent(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.back:
               this.finish();
               break;
           case R.id.car1:
           case R.id.car2:
           case R.id.car3:
               clickView = (ImageView) v;
               menuWindow.showAtLocation(
                       v,
                       Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
               break;
           case R.id.btn_one:
               photosUtil = new PhotosUtil(clickView,this,this,null);
               photosUtil.photoPicke();
               menuWindow.dismiss();
               break;
           case R.id.btn_two:
               photosUtil = new PhotosUtil(clickView,this,this,null);
               photosUtil.takePhoto();
               menuWindow.dismiss();
               break;
           case R.id.btn_three:
               menuWindow.dismiss();
               break;
       }
    }
    @Override
    public void onPhotoReturn(Uri imageUri) {
        if(imageUri!=null&&clickView!=null){
            Log.e("onPhotoReturn",imageUri.toString());
            Bitmap bitmap = null;
            try {
                bitmap = BitmapUtil.getBitmapFormUri(this,imageUri,clickView.getHeight(),clickView.getWidth());
            } catch (IOException e) {

            }
            if(bitmap!=null&&!bitmap.isRecycled()){
                clickView.setBackgroundColor(Color.TRANSPARENT);
                clickView.setImageBitmap(bitmap);
            }

        }
    }
}
