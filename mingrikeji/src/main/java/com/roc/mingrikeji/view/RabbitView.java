package com.roc.mingrikeji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.roc.mingrikeji.R;

/**
 * Created by Administrator on 2016/11/5.
 */

public class RabbitView extends View {
    public float bitmapX;//小兔子显示位置的x坐标
    public float bitmapY;//小兔子显示的y坐标
    public RabbitView(Context context) {
        super(context);
        bitmapX=750;
        bitmapY=500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.rabbit);
        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);
        if(bitmap.isRecycled()){
            bitmap.recycle();//判断图片是否回收，强制回收图片
        }
    }
}
