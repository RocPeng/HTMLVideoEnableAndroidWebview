package com.roc.mingrikeji;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/11/5.
 */

public class MainActivity extends AppCompatActivity {
    //context
    private Context mContext;
    private TextView text;
    private ProgressBar p1;
    private ProgressBar p2;
    private ProgressBar p3;
    private int mProgressStatus=0;
    private Handler mHandler;
    private SeekBar mySeekBar;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private NotificationManager mManager;
    private TextView xmlText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeActionOverflowMenuShown();
        setContentView(R.layout.activity_main);
        mContext=this;
        text=(TextView)findViewById(R.id.text);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        xmlText=(TextView)findViewById(R.id.xmlText);
        Spinner mySpinner=(Spinner)findViewById(R.id.mySpinner);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> view, View view1, int i, long l) {
                String result=view.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> view) {
            }
        });

        //方法二，在java'文件中为spinner创建适配器
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(MainActivity.this,R.array.ctype,
                android.R.layout.simple_dropdown_item_1line);
        //还可以直接使用一位数组当做资源创建
        String[] ctype=new String[]{"学生证","身份证","其他"};
        ArrayAdapter<CharSequence> adapter2=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,
                ctype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        //日期等
        DatePicker date=(DatePicker)findViewById(R.id.date);
        TimePicker time=(TimePicker)findViewById(R.id.time);
        time.setIs24HourView(true);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        //计时器的用法
        final Chronometer myChronometer=(Chronometer)findViewById(R.id.mychronometer);
        myChronometer.setBase(SystemClock.elapsedRealtime());//设置起始时间
        myChronometer.setFormat("已用时间:%s");
        myChronometer.start();
        myChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(SystemClock.elapsedRealtime()-myChronometer.getBase()>=1000){
                    myChronometer.stop();
                }
            }
        });
        //初始化进度条
        progressBarTest();
        //开启线程模拟耗时操作
        startNewThread();
        //水平拖动条的测试
        seekBarTest();
        //Toast测试
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast=new Toast(mContext);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                LinearLayout layout=new LinearLayout(mContext);
                ImageView image=new ImageView(mContext);
                image.setImageResource(R.drawable.icon_dna_small);
                image.setPadding(0,0,5,0);
                layout.addView(image);
                TextView text=new TextView(mContext);
                text.setText("构造方法创建Toast!");
                layout.addView(text);
                toast.setView(layout);
                toast.show();
            }
        });
        //通知栏消息显示 测试
        mManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        noticeTest();
        //各种alertDialog展示
        findViewById(R.id.btn4).setOnClickListener(mClickListener);
        findViewById(R.id.btn5).setOnClickListener(mClickListener);
        findViewById(R.id.btn6).setOnClickListener(mClickListener);
        findViewById(R.id.btn7).setOnClickListener(mClickListener);
        findViewById(R.id.btn8).setOnClickListener(mClickListener);
        //上下文菜单(三种方式：选项菜单，上下文菜单，带子菜单的选项菜单)
        TextView text9=(TextView)findViewById(R.id.btn9);
        text9.setOnClickListener(mClickListener);
        registerForContextMenu(text9);
    }

    private void progressBarTest(){
         p1=(ProgressBar) findViewById(R.id.progressBar1);
         p2=(ProgressBar) findViewById(R.id.progressBar2);
         p3=(ProgressBar) findViewById(R.id.progressBar3);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x111){
                    p1.setProgress(mProgressStatus);
                }else{
                    Toast.makeText(mContext, "耗时操作结束", Toast.LENGTH_SHORT).show();
                    p1.setVisibility(View.GONE);
                    p2.setVisibility(View.GONE);
                    p3.setVisibility(View.GONE);
                }
            }
        };
    }

    private void startNewThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    mProgressStatus += doWork();
                    Message m = new Message();
                    if (mProgressStatus < 100) {
                        m.what = 0x111;
                        mHandler.sendMessage(m);
                    } else {
                        m.what = 0x110;
                        mHandler.sendMessage(m);
                        break;
                    }
                }
            }
            //m模拟一个耗时操作
            private int doWork(){
                mProgressStatus+=Math.random()*10;
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                return mProgressStatus;
            }
        }).start();
    }
    private void seekBarTest(){
        mySeekBar=(SeekBar)findViewById(R.id.mySeekBar);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int i, boolean b) {
                text.setText("当前进度："+i+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar bar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {

            }
        });
    }

    private void noticeTest(){
        btn2.setOnClickListener(mClickListener);
        btn3.setOnClickListener(mClickListener);
    }

    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getGroupId()==R.id.setting){
            if(item.isChecked()){
                item.setChecked(false);
            }else{
                item.setChecked(true);
            }
        }
        if (item.getItemId()!=R.id.item2){
            Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflator=new MenuInflater(mContext);
        inflator.inflate(R.menu.menus,menu);
        menu.setHeaderIcon(R.drawable.icon_dna_small);
        menu.setHeaderTitle("请选择!");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }

    //屏蔽后退键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Toast.makeText(mContext, "后退键已被屏蔽", Toast.LENGTH_SHORT).show();
            return  true;
        }
        return super.onKeyDown(keyCode,event);
    }

    private View.OnClickListener mClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn2:
                    NotificationCompat. Builder builder=new NotificationCompat.Builder(mContext);
                    builder.setContentTitle("我是标题").setContentText("我是通知内容").setSmallIcon(R.drawable.icon_dna_small);
                    Intent intent=new Intent(mContext,RabbitViewActivity.class);
                    PendingIntent notifyIntent=PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(notifyIntent);
                    Notification notification=builder.build();
                    mManager.notify(1,notification);
                     break;
                case R.id.btn3:
                    mManager.cancel(1);
                    mManager.cancelAll();
                    break;
                case R.id.btn4:
                    AlertDialog dialog=new AlertDialog.Builder(mContext).create();
                    dialog.setIcon(R.drawable.icon_dna_small);
                    dialog.setTitle("dialog标题");
                    dialog.setMessage("带确定，取消，中立的对话框");
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            Toast.makeText(mContext, "单击了取消按钮", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            Toast.makeText(mContext, "单击了中立按钮", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            Toast.makeText(mContext, "单击了确定按钮", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                    break;
                case R.id.btn5:
                    final  String[] items=new String[]{"羽毛球","篮球","乒乓球","足球"};
                    AlertDialog.Builder builder2=new AlertDialog.Builder(mContext);
                    builder2.setIcon(R.drawable.icon_dna_small);
                    builder2.setTitle("热爱的运动是什么?");
                    builder2.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            Toast.makeText(mContext, "你选择了:"+items[i], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder2.create().show();
                    break;
                case R.id.btn6:
                    final  String[] items3=new String[]{"标准","无声","会议","户外"};
                    //显示带单选列表项的对话框
                    AlertDialog.Builder builder3=new AlertDialog.Builder(mContext);
                    builder3.setIcon(R.drawable.icon_dna_small);
                    builder3.setTitle("要使用的情景模式?");
                    builder3.setSingleChoiceItems(items3, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            Toast.makeText(mContext, "您选择了:"+items3[i], Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder3.setPositiveButton("确定",null);
                    builder3.create().show();
                    break;
                case R.id.btn7:
                    Intent intent2=new Intent();
                    intent2.setAction(Intent.ACTION_VIEW);
                    startActivity(intent2);
                    break;
                case R.id.btn8:
                    XmlResourceParser xrp=getResources().getXml(R.xml.customers);
                    StringBuilder sb=new StringBuilder("");
                    try{
                        //如果没有到 xml文档结尾处
                        while(xrp.getEventType()!=XmlResourceParser.END_DOCUMENT){
                            if (xrp.getEventType()==XmlResourceParser.START_TAG){//判断是否为开始标记
                                String tagName=xrp.getName();//获取标记名
                                if(tagName.equals("customer")){
                                    sb.append("姓名："+xrp.getAttributeValue(0)+"  ");
                                    sb.append("联系电话："+xrp.getAttributeValue(1)+"  ");
                                    sb.append("E-Mail："+xrp.getAttributeValue(2)+"  ");
                                    sb.append("\n");
                                }
                            }
                            xrp.next();
                        }
                        xmlText.setText(sb);
                    }catch (XmlPullParserException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();;
                    }
                    break;
                 default: break;
            }
        }
    };

}
