package com.aiseminar.platerecognizer.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.util.BitmapUtil;
import com.aiseminar.platerecognizer.util.PhotosUtil;
import com.aiseminar.platerecognizer.views.SelectPicPopupWindow;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/10.
 */

public class ReportFrag extends Fragment implements View.OnClickListener,PhotosUtil.OnPhotoReturn{
    private SelectPicPopupWindow menuWindow;
    private ImageView clickView;
    private PhotosUtil photosUtil;

    public ReportFrag() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_report,null);
        findView(rootView);
        return rootView;
    }

    private void findView(View rootView) {
       rootView.findViewById(R.id.pic1).setOnClickListener(this);
       rootView.findViewById(R.id.pic2).setOnClickListener(this);
       rootView.findViewById(R.id.pic3).setOnClickListener(this);
        menuWindow = new SelectPicPopupWindow(getActivity(),
                this, "从相册选择", "拍照", "取消", null);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        photosUtil.DistributeIntent(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pic1:

            case R.id.pic2:

            case R.id.pic3:
                clickView = (ImageView) v;
                menuWindow.showAtLocation(
                        v,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_one:
                photosUtil = new PhotosUtil(clickView,getActivity(),ReportFrag.this,ReportFrag.this);
                photosUtil.photoPicke();
                menuWindow.dismiss();
                break;
            case R.id.btn_two:
                photosUtil = new PhotosUtil(clickView,getActivity(),ReportFrag.this,ReportFrag.this);
                photosUtil.takePhoto();
                menuWindow.dismiss();
                break;
            case R.id.btn_three:
                menuWindow.dismiss();
                break;
            case R.id.commit:
                break;
        }
    }

    @Override
    public void onPhotoReturn(Uri imageUri) {
      if(imageUri!=null&&clickView!=null){
          Log.e("onPhotoReturn",imageUri.toString());
          Bitmap bitmap = null;
          try {
              bitmap = BitmapUtil.getBitmapFormUri(getActivity(),imageUri,clickView.getHeight(),clickView.getWidth());
          } catch (IOException e) {

          }
          if(bitmap!=null&&!bitmap.isRecycled()){
              clickView.setImageBitmap(bitmap);
          }

      }
    }
}
