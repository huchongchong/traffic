package com.aiseminar.platerecognizer.views;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/17.
 */

public class MyTextView extends TextView {
    private TextPaint paint;

    public MyTextView(Context context) {
        super(context);
        Log.e("hss","a");
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("hss","a");
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("hss","a");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int a = canvas.getClipBounds().bottom;
        int b = canvas.getClipBounds().top;
        int c = canvas.getClipBounds().height();
        Log.e("hss","a"+a+"b"+b+"c"+c);
        super.onDraw(canvas);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
