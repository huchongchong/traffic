package com.aiseminar.platerecognizer.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/8/3.
 */
public class PhotosUtil {
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Fragment fragment;
    private  OnPhotoReturn onPhotoReturn;
    private  Activity activity;
    private  ImageView targetImg;
    public Uri  imageUri = Uri.parse("file:///sdcard/makertemp.jpg");
    public String path;

    public PhotosUtil(ImageView img, Activity activity, OnPhotoReturn onPhotoReturn, Fragment fragment) {
        this.targetImg = img;
        this.activity = activity;
        this.onPhotoReturn = onPhotoReturn;
        this.fragment = fragment;
        if (hasSdcard()) {
          path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/traffic";
        }
    }
   public void destory(){
       this.targetImg = null;
       this.activity = null;
   }
    public void targetImg(ImageView img){
        this.targetImg = img;
    }
    private void startCrop(Uri data,ImageView headicon_Img) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", headicon_Img.getWidth());
        intent.putExtra("outputY", headicon_Img.getHeight());
        intent.putExtra("return-data", true);
        if(fragment!=null) {
            fragment.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        }else{
            activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        }
    }
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        // 有存储的SDCard
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    public  void takePhoto(){
        try {
            if (hasSdcard()) {
                File file = new File(path);
                if(!file.exists()){
                    file.mkdirs();
                }
                File outFile = new File(path, System.currentTimeMillis() + ".jpg");
                imageUri = Uri.fromFile(outFile);
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                if(fragment!=null) {
                    fragment.startActivityForResult(intentCamera, PHOTO_REQUEST_TAKEPHOTO);
                }else{
                    activity.startActivityForResult(intentCamera, PHOTO_REQUEST_TAKEPHOTO);
                }
            } else {
                Toast.makeText(activity, "请确认已安装内存卡", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(activity, "请确认已安装内存卡", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public  void photoPicke(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        if(fragment!=null) {
            fragment.startActivityForResult(photoPickerIntent, PHOTO_REQUEST_GALLERY);
        }else{
            activity.startActivityForResult(photoPickerIntent, PHOTO_REQUEST_GALLERY);
        }
    }
    public  void DistributeIntent(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
//                    startCrop(imageUri, targetImg);
                    if(resultCode==Activity.RESULT_OK) {
                        if (this.onPhotoReturn != null) onPhotoReturn.onPhotoReturn(imageUri);
                    }else{
                        Toast.makeText(fragment.getActivity(),"取消拍摄的照片",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                    if(resultCode==Activity.RESULT_OK) {
                        Uri imgUri = data.getData();
//                    startCrop(imgUri, targetImg);
                        if(this.onPhotoReturn!=null)onPhotoReturn.onPhotoReturn(imgUri);
                    }else{
                        Toast.makeText(fragment.getActivity(),"取消选择的照片",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_CUT:// 返回的结果
                    if(this.onPhotoReturn!=null)onPhotoReturn.onPhotoReturn(imageUri);
                    break;
            }
        }
    }
    public interface OnPhotoReturn{
        void onPhotoReturn(Uri imageUri);
    }
}
