package com.aiseminar.platerecognizer.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ComDialog extends Dialog {
    private  Params mParams;
    private Button bt;
    private View mDialogContentView;
    public int showType;

    public ComDialog(Params params) {
        super(params.context);
        if(params.style>0)
        mParams = params;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initDialog();
    }
    public ComDialog(Params params, int style) {
        super(params.context,params.style);
        if(params.style>0)
        mParams = params;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initDialog();
    }

    private void initDialog() {
        RelativeLayout container = new RelativeLayout(getContext());
        mDialogContentView = LayoutInflater.from(getContext()).inflate(mParams.dialogViewResource, container, false);
        RelativeLayout.LayoutParams contentParams = (RelativeLayout.LayoutParams) mDialogContentView.getLayoutParams();
        switch (mParams.gravity) {
            case Gravity.CENTER:
                contentParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
            case Gravity.LEFT:
                contentParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                contentParams.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case Gravity.TOP:
                contentParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                contentParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case Gravity.RIGHT:
                contentParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                contentParams.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case Gravity.BOTTOM:
                contentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                contentParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
        }
        mDialogContentView.setLayoutParams(contentParams);
        container.addView(mDialogContentView);
        setContentView(container);
        if(mParams.isCancelable) {
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateDismiss();
                }
            });
            mDialogContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (mParams.clickViews != null) {
            Iterator iterator =mParams.clickViews.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                Integer key = (Integer)entry.getKey();
                View.OnClickListener value = (View.OnClickListener) entry.getValue();
                mDialogContentView.findViewById(key).setOnClickListener(value);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                if (mParams.startTechnique != null) {
                    YoYo.with(mParams.startTechnique).duration(mParams.startDuration).interpolate(new AccelerateDecelerateInterpolator()).playOn(mDialogContentView);
                }
                if (mParams.showListener != null) {
                    mParams.showListener.onShow(null);
                }
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mParams.dismissListener != null) {
                    mParams.dismissListener.onDismiss(null);
                }
            }
        });
    }
    /**
     * 当点击返回或重复点击菜单键时执行dismiss动画并dismiss
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            animateDismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 执行dismiss动画，并dismiss
     */
    public void animateDismiss() {
        if (mParams.endTechnique != null) {
            YoYo.with(mParams.endTechnique).duration(mParams.endDuration).interpolate(new AccelerateDecelerateInterpolator()).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator arg0) {
                }

                @Override
                public void onAnimationRepeat(Animator arg0) {
                }

                @Override
                public void onAnimationEnd(Animator arg0) {
                    dismiss();
                }

                @Override
                public void onAnimationCancel(Animator arg0) {
                }
            }).playOn(mDialogContentView);
        } else {
            dismiss();
        }
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public static class Params {
        private int dialogViewResource;
        private Context context;
        private OnShowListener showListener;
        private OnDismissListener dismissListener;
        private int gravity = Gravity.CENTER;
        private Techniques startTechnique;
        private int startDuration;
        private Techniques endTechnique;
        private int endDuration;
        private int style;
        private boolean isCancelable = true;
        private HashMap<Integer, View.OnClickListener> clickViews;
    }

    public static class Builder {
        private Params params;

        public Builder(Context context) {
            params = new Params();
            params.startDuration = context.getResources().getInteger(android.R.integer.config_longAnimTime);
            params.endDuration = context.getResources().getInteger(android.R.integer.config_longAnimTime);
            params.context = context;
        }

        public Builder setGravity(int gravity) {
            if (gravity != Gravity.CENTER && gravity != Gravity.LEFT && gravity != Gravity.TOP && gravity != Gravity.RIGHT && gravity != Gravity.BOTTOM) {
                throw new IllegalArgumentException("Dialog Gravity error");
            }
            params.gravity = gravity;
            return this;
        }

        public Builder setStartDuration(int duration) {
            if (duration > 0) {
                params.startDuration = duration;
            }
            return this;
        }
        public Builder setStyle(int style) {
            if (style > 0) {
                params.style = style;
            }
            return this;
        }

        public Builder setEndDuration(int duration) {
            if (duration > 0) {
                params.endDuration = duration;
            }
            return this;
        }

        public Builder setDialogView(int dialogView) {
            params.dialogViewResource = dialogView;
            return this;
        }

        public Builder setStartTechnique(Techniques technique) {
            params.startTechnique = technique;
            return this;
        }
        public Builder setViewClickLinstener(int vId,View.OnClickListener listener){
              if(params.clickViews==null){
                  params.clickViews = new HashMap();;
              }
            params.clickViews.put(vId, listener);
            return this;
        }
        public Builder setEndTechnique(Techniques technique) {
            params.endTechnique = technique;
            return this;
        }

        public Builder setOnShowListener(OnShowListener listener) {
            params.showListener = listener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            params.dismissListener = listener;
            return this;
        }
        public Builder setIsCancelable(boolean isCancelable) {
            params.isCancelable = isCancelable;
            return this;
        }

        public ComDialog build() {
            if(params.style>0)
            return new ComDialog(params,1);
            else return new ComDialog(params);
        }
    }
}
