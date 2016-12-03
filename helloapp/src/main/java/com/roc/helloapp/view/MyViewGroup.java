package com.roc.helloapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/20.
 */

public class MyViewGroup extends ViewGroup {
    private static final String TAG = "EVENT:";
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureWidth(heightMeasureSpec));
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    public int measureWidth(int measureSpec){
        int result=0;
        int specMode=MeasureSpec.getMode(measureSpec);
        int specSize=MeasureSpec.getSize(measureSpec);
        if(specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result=200;
            if(specMode==MeasureSpec.AT_MOST){
                result=Math.min(result,specSize);
            }
        }
        return result;
    }


    @Override
    protected void onLayout(boolean b, int i0, int i1, int i2, int i3) {
        //记录总高度
        int mTotalHeight=0;
        //遍历子视图
        int childCount=getChildCount();
        for(int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            //获取在onMeasure中计算的试图尺寸
            int measureWidth=childView.getMeasuredWidth();
            int measureHeight=childView.getMeasuredHeight();
            childView.layout(i0,mTotalHeight,measureWidth,mTotalHeight+measureHeight);
            mTotalHeight+=measureHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"ViewGroup:dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"ViewGroup:onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"ViewGroup:onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
