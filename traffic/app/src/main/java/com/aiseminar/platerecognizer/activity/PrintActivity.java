package com.aiseminar.platerecognizer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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


public class PrintActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        initView();
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.back:
               this.finish();
               break;
           case R.id.print:
               Intent intent = new Intent(this,TakePictureActivity.class);
               startActivity(intent);
               break;
       }
    }
}
