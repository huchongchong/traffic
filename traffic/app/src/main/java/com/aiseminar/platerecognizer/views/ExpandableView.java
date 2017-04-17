package com.aiseminar.platerecognizer.views;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiseminar.platerecognizer.R;


public class ExpandableView extends LinearLayout implements
        View.OnClickListener {

    private static final String TAG = ExpandableView.class.getSimpleName();

    /* The default number of lines */

    /* The default animation duration */
    private static final int DEFAULT_ANIM_DURATION = 150;

    /* The default alpha value when the animation starts */
    private static final float DEFAULT_ANIM_ALPHA_START = 1f;

    protected View mView1,mViewLine;
    protected LinearLayout mView2;
    private boolean mCollapsed = true; // Show short version as default.
    private int mCollapsedHeight = -1;
    private float mAnimAlphaStart =1;
    private boolean mAnimating;
    private int mView2H;
    private OnClickListener onClickListener;
    private OnViewExpandStateChangeListener onViewExpandStateChangeListener;

    public ExpandableView(Context context) {
        this(context, null);
    }

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ExpandableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    @Override
    public void setOrientation(int orientation) {
        if (LinearLayout.HORIZONTAL == orientation) {
            throw new IllegalArgumentException(
                    "ExpandableOtherView only supports Vertical Orientation.");
        }
        super.setOrientation(orientation);
    }
    public void close(){
        setzeorHeight();
        mCollapsed = true;
    }
    @Override
    public void onClick(View view) {
        if(mAnimating){
            return;
        }
        mAnimating = true;
        Animation animation;
        if (mCollapsed) {
            animation = new ExpandCollapseAnimation1(this, mView1.getMeasuredHeight()+mViewLine.getMeasuredHeight(),mView1.getMeasuredHeight()+mView2.getMeasuredHeight()+mViewLine.getMeasuredHeight());
        } else {
            animation = new ExpandCollapseAnimation1(this, mView1.getMeasuredHeight()+mView2.getMeasuredHeight()+mViewLine.getMeasuredHeight(),
                    mView1.getMeasuredHeight()+mViewLine.getMeasuredHeight());
        }
        mCollapsed = !mCollapsed;
        if(this.onViewExpandStateChangeListener!=null){
            this.onViewExpandStateChangeListener.onViewExpandStateChanged(view,mCollapsed);
        }
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                applyAlphaAnimation(mView2, mAnimAlphaStart);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                mAnimating = false;
               if(mCollapsed){
            }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        clearAnimation();
        startAnimation(animation);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mAnimating){
            return mAnimating;
        }
        return super.onInterceptTouchEvent(ev);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mCollapsedHeight == -1) {
            mCollapsedHeight = getMeasuredHeight();
        }
    }
     private void setzeorHeight(){
         this.getLayoutParams().height = mView1.getHeight()+mViewLine.getHeight();
         this.requestLayout();
     }
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        if(mCollapsedHeight==-1){
            mCollapsedHeight = getMeasuredHeight();
        }
        super.onDraw(canvas);
    }

    private void init(AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        setVisibility(View.VISIBLE);
        this.setOnClickListener(this);
    }
    public void setclickView(View v, OnClickListener onClickListener){
        v.setOnClickListener(onClickListener);
    }
    private void findViews() {
        mView1 = this.findViewById(R.id.view1);
        mView2 = (LinearLayout) this.findViewById(R.id.view2);
        mViewLine= this.findViewById(R.id.line);
//        final ViewTreeObserver vto = mView2.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Log.e("hss",mView2H+"  height ddd "+mView2.getMeasuredHeight());
//                if(vto.isAlive()){
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        vto.removeOnGlobalLayoutListener(this);
//                    }else {
//                        vto.removeGlobalOnLayoutListener(this);
//                    }
//                }else{
//                    Log.e("hss","vto"+vto.isAlive());
//                    mView2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                }
//                if(mView2H==0) {
//                    mView2H = mView2.getMeasuredHeight();
//                }
//                close();
//            }
//        });
    }
    private static boolean isPostHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private static boolean isPostLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void applyAlphaAnimation(View view, float alpha) {
        if (isPostHoneycomb()) {
            view.setAlpha(alpha);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            view.startAnimation(alphaAnimation);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable getDrawable(@NonNull Context context,
                                        @DrawableRes int resId) {
        Resources resources = context.getResources();
        if (isPostLolipop()) {
            return resources.getDrawable(resId, context.getTheme());
        } else {
            return resources.getDrawable(resId);
        }
    }
    class ExpandCollapseAnimation1 extends Animation {
        private final View mTargetView;
        private final int mStartHeight;
        private final int mEndHeight;

        public ExpandCollapseAnimation1(View view, int startHeight, int endHeight) {
            mTargetView = view;
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            setDuration(150);
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            final int newHeight = (int) ((mEndHeight - mStartHeight)
                    * interpolatedTime + mStartHeight);
            if (Float.compare(mAnimAlphaStart, 1.0f) != 0) {
                applyAlphaAnimation(mView2, mAnimAlphaStart + interpolatedTime
                        * (1.0f - mAnimAlphaStart));
            }
            mTargetView.getLayoutParams().height = newHeight;
            mTargetView.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
    public void setListener(OnViewExpandStateChangeListener onViewExpandStateChangeListener,OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        this.onViewExpandStateChangeListener= onViewExpandStateChangeListener;
    }
    public interface OnViewExpandStateChangeListener {
        /**
         * Called when the expand/collapse animation has been finished
         view
         * @param
         *            - TextView being expanded/collapsed
         * @param isExpanded
         *            - true if the TextView has been expanded
         */
        void onViewExpandStateChanged(View view, boolean isExpanded);
    }
}