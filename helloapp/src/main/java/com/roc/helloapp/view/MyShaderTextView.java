package com.roc.helloapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/20.
 */

public class MyShaderTextView extends TextView {
    private int mViewWidth=0;
    private Matrix mGradientMatrix;
    private LinearGradient mLinearGradient;
    public MyShaderTextView(Context context) {
        super(context);
    }

    public MyShaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyShaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mGradientMatrix!=null){
            int mTranslate=0;
            mTranslate +=mViewWidth/5;
            if(mTranslate>2*mViewWidth){
                mTranslate=-mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth==0){
            mViewWidth=getMeasuredWidth();
            if(mViewWidth>0){
                Paint mPaint=getPaint();
                mLinearGradient=new LinearGradient(0,0,mViewWidth,0,
                        new int[]{Color.BLUE,0xffffffff,Color.BLUE},null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix=new Matrix();
            }
        }
    }
}
