package com.roc.mingrikeji;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FrameLayoutActivity extends AppCompatActivity {
    public TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout=new FrameLayout(this);
        frameLayout.setBackground(this.getResources().getDrawable(R.drawable.background_white));
        setContentView(frameLayout);
        //声明text1并添加属性：文字，大小（24px），颜色
        TextView text1=new TextView(this);
        text1.setText("代码中控制UI界面");
        text1.setTextSize(TypedValue.COMPLEX_UNIT_PX,24);
        text1.setTextColor(Color.rgb(1,1,1));
        frameLayout.addView(text1);
        //text2 并添加事件处理
        text2=new TextView(this);
        text2.setText("单击进入游戏....");
        text2.setTextSize(TypedValue.COMPLEX_UNIT_PX,24);
        text2.setTextColor(Color.rgb(1,1,1));
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
        text2.setLayoutParams(params);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(FrameLayoutActivity.this).setTitle("系统提示")
                        .setMessage("游戏有风险，进入需谨慎，是否进入?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                Toast.makeText(FrameLayoutActivity.this,"即将进入游戏",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface anInterface, int i) {
                        Toast.makeText(FrameLayoutActivity.this,"即将退出游戏",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).show();
            }
        });
        frameLayout.addView(text2);
    }
}
