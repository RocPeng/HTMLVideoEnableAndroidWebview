package com.roc.helloapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/11/20.
 */

public class CircleTestView extends View {
    public CircleTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mHeight=getHeight();
        int mWidth=getWidth();
        Paint circlePaint=new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.RED);
        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2,circlePaint);
        //开始画刻度
        Paint linePaint=new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5);
        for(int i=0;i<12;i++){
            canvas.drawLine(mWidth/2,mHeight/2-mWidth/2,mWidth/2,mHeight/2-mWidth/2+30,linePaint);
            canvas.rotate(360/12,mWidth/2,mHeight/2);
        }
        canvas.translate(mWidth/2,mHeight/2);
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(7);
        canvas.drawLine(0,0,100,0,linePaint);
        canvas.drawLine(0,0,0,200,linePaint);
    }
}
