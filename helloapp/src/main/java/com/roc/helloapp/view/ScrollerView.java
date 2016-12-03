package com.roc.helloapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/11/20.
 */

public class ScrollerView extends View {
    private int lastX;
    private int lastY;
    private Scroller mScroller;
    public ScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
    }

    public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller=new Scroller(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("ScrollerActivity","onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        Log.d("ScrollerActivity","computeScroll");
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int)event.getX();
        int y=(int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=x;
                lastY=y;
                Log.d("ScrollerActivity","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX=x-lastX;
                int offsetY=y-lastY;
                ( (View)getParent()).scrollBy(-offsetX,-offsetY);
                Log.d("ScrollerActivity","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ScrollerActivity","ACTION_UP");
                View viewGroup=(View)getParent();
                mScroller.startScroll(viewGroup.getScrollX(),viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),-viewGroup.getScrollY());
                invalidate();
                break;
        }
        return true;
    }
}
