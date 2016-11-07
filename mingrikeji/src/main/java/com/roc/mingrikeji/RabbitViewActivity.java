package com.roc.mingrikeji;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.roc.mingrikeji.view.RabbitView;

/**
 * Created by Administrator on 2016/11/5.
 */

public class RabbitViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout=new FrameLayout(this);
        frameLayout.setBackground(this.getResources().getDrawable(R.drawable.background));
        setContentView(frameLayout);
        final RabbitView rabbit=new RabbitView(RabbitViewActivity.this);
        //小兔子添加监听事件
        rabbit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                rabbit.bitmapX=event.getX();
                rabbit.bitmapY=event.getY();
                rabbit.invalidate();
                return true;
            }
        });
        frameLayout.addView(rabbit);
    }
}
