package com.aiseminar.platerecognizer.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by ares on 6/20/16.
 */
public class FileUtil {
    public static final int FILE_TYPE_IMAGE = 1;
    public static final int FILE_TYPE_PLATE = 2;
    public static final int FILE_TYPE_SVM_MODEL = 3;
    public static final int FILE_TYPE_ANN_MODEL = 4;
    public static final int FILE_TYPE_SAVE = 5;

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = null;
        switch (type) {
            case FILE_TYPE_IMAGE: {
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "PlateRcognizer");
                break;
            }
            case FILE_TYPE_PLATE: {
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "PlateRcognizer/PlateRect");
                break;
            }
            case FILE_TYPE_ANN_MODEL:
            case FILE_TYPE_SVM_MODEL: {
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        "PlateRcognizer");
                break;
            }
            case FILE_TYPE_SAVE:
//                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
//                   MyContext.getInstance().playerId+"/"+MyContext.getInstance().createOrderId() );
                break;
            default:
                return null;
        }
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()) {
            if (! mediaStorageDir.mkdirs()) {
                Log.d("PlateRcognizer", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = DateUtil.getDateFormatString(new Date());
        File mediaFile;
        switch (type) {
            case FILE_TYPE_IMAGE: {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "RPK_" + timeStamp + ".jpg");
                break;
            }
            case FILE_TYPE_PLATE: {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "RP_" + timeStamp + ".jpg");
                break;
            }
            case FILE_TYPE_ANN_MODEL: {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "ann.xml");
                break;
            }
            case FILE_TYPE_SVM_MODEL: {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "svm.xml");
                break;
            }
            case FILE_TYPE_SAVE: {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "his_" + timeStamp + ".jpg");
                break;
            }
            default:
                return null;
        }

        return mediaFile;
    }

    public static String getMediaFilePath(int type) {
        File mediaStorageDir = null;
        File mediaFile;
        switch (type) {
            case FILE_TYPE_ANN_MODEL: {
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        "PlateRcognizer");
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "ann.xml");
                break;
            }
            case FILE_TYPE_SVM_MODEL: {
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        "PlateRcognizer");
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "svm.xml");
                break;
            }
            default:
                return null;
        }
        return mediaFile.getAbsolutePath();
    }

    public static boolean copyFile(String srcFileName,String destFileName,boolean overlay){
             File srcFile = new File(srcFileName);

            if(!srcFile.exists()){
                       return false;
            }else if(!srcFile.isFile()){
                      return false;
            }

            File destFile = new File(destFileName);
        if(destFile.exists()){
            if(overlay){
                new File(destFileName).delete();
            }}else{

            if(!destFile.getParentFile().exists()){
                if(!destFile.getParentFile().mkdirs()){
                    return false;
                }
            }
        }

      int byteread = 0;
       InputStream in = null;
        OutputStream out = null;

        try{
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            while((byteread=in.read(buffer))!=-1){
                out.write(buffer,0,byteread);
                }
            return true;
            }catch (FileNotFoundException e){
            return false;
            }catch (IOException e){
                  return false;
            }finally{
            try{
                if(out!=null)
                out.close();
                if(in!=null)
                in.close();
                }catch(IOException e){
                e.printStackTrace();
                }
            }
        }
    public static String getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
//            String byteString = Base64.encodeBase64String(buffer);
            return "byteString";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isCarnumberNO(String carnumber) {


        String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
              if (TextUtils.isEmpty(carnumber)) return false;
              else return carnumber.matches(carnumRegex);
    }
}
