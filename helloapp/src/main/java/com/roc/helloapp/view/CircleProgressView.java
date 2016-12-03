package com.roc.helloapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/11/22.
 */

public class CircleProgressView extends View {
    private int mMaxProgress=100;
    private int mProgress=0;
    private final int mCircleLineStrokeWidth=8;
    private final int mTxtStokrWidth=2;
    //画圆所在的矩形区域
    private final RectF mRectF;
    private final Paint mPaint;
    private Context mContext;
    private String text;

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        mRectF=new RectF();
        mPaint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=this.getWidth();
        int height=this.getHeight();
        if(width!=height){
            int min=Math.min(width,height);
            width=min;
            height=min;
        }
        //画笔相关属性:空心并设置线宽
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.rgb(0xe5,0xe5,0xe5));
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        //位置
        mRectF.left=mCircleLineStrokeWidth/2;
        mRectF.top=mCircleLineStrokeWidth/2;
        mRectF.right=width-mCircleLineStrokeWidth/2;
        mRectF.bottom=width-mCircleLineStrokeWidth/2;
        //绘制圆圈，进度条背景
        canvas.drawArc(mRectF,-90,360,false,mPaint);
        mPaint.setColor(Color.rgb(0x65,0xd5,0x21));
        canvas.drawArc(mRectF,-90,((float)mProgress/mMaxProgress)*360,false,mPaint);
        //绘制text
        mPaint.setStrokeWidth(mTxtStokrWidth);
        text=mProgress+"%";
        int textHeight=height/4;
        mPaint.setTextSize(textHeight);
        int textWidth=(int)mPaint.measureText(text);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,width/2-textWidth/2,height/2+textHeight/2- mPaint.getFontMetrics().descent,mPaint);
    }
    public int getMaxProgress(){
        return  mMaxProgress;
    }
    public void setMaxProgress(int maxProgress){
        this.mMaxProgress=maxProgress;
    }
    public void setProgress(int progress){
        this.mProgress=progress;
        this.invalidate();
    }
    public void setProgressNotInUiThread(int progress){
        this.mProgress=progress;
        this.postInvalidate();
    }
}
