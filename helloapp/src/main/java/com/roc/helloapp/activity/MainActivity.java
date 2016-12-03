package com.roc.helloapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roc.helloapp.R;
import com.roc.helloapp.view.CircleProgressView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private String urlStr;
    private CircleProgressView myProgressBar;
    private EditText urlText;
    private TextView rateText;
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProgressBar=(CircleProgressView)findViewById(R.id.MyProgressBar);
        urlText=(EditText)findViewById(R.id.URLText);
        rateText=(TextView)findViewById(R.id.rate);
        download=(Button)findViewById(R.id.download);
        download.setOnClickListener(click);
        this.findViewById(R.id.next).setOnClickListener(click);
        this.findViewById(R.id.down).setOnClickListener(click);
        this.findViewById(R.id.ScrollerActivity).setOnClickListener(click);
        this.findViewById(R.id.DragViewTest).setOnClickListener(click);
        this.findViewById(R.id.DragViewGroup).setOnClickListener(click);
        this.findViewById(R.id.CircleTest).setOnClickListener(click);
    }

    private View.OnClickListener click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            switch (view.getId()){
                case R.id.download:
                    //开始执行下载操作
                    urlStr=urlText.getText().toString();
//                    new DownloadTask(urlStr).execute();
                    downTest(urlStr);
                    break;
                case R.id.next:
                    intent.setClass(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.down:
                    intent.setClass(MainActivity.this,DownLoadListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ScrollerActivity:
                    intent.setClass(MainActivity.this,ScrollerActivity.class);
                    startActivity(intent);
                    break;
                case R.id.DragViewTest:
                    intent.setClass(MainActivity.this,DragViewTestActivity.class);
                    startActivity(intent);
                    break;
                case R.id.DragViewGroup:
                    intent.setClass(MainActivity.this,DragViewGroupActivity.class);
                    startActivity(intent);
                    break;
                case R.id.CircleTest:
                    intent.setClass(MainActivity.this,CircleTestActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    public void downTest(String str){
        String  urlStr="http://echat-sit.oss-cn-hangzhou.aliyuncs.com/" +
                "echat_sit/Attachment/58356fed8e824138ef3e86f0";
        Request request=new Request.Builder().url(urlStr).build();
//        Retrofit retrofit=new Retrofit.Builder().baseUrl(urlStr)
//                .addConverterFactory(Json)
    }

    public class DownloadTask extends AsyncTask{
        private URL url;
        public DownloadTask(String urlStr){
            try{
                urlStr="http://echat-sit.oss-cn-hangzhou.aliyuncs.com/echat_sit/Attachment/58356fed8e824138ef3e86f0";
                this.url= new URL(urlStr);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try{
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
//                con.setDoInput(true);
//                con.setDoOutput(true);
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setRequestMethod("GET");
                int fileLength=con.getContentLength();
                con.connect();
                //获取头
                Map<String,List<String>> headers=con.getHeaderFields();
                Log.d("fileProperty:",headers.toString());
                //获取返回的输入流
                String filePath= Environment.getExternalStorageDirectory().getPath()+"/test.rar";
                String folderPath=getApplicationContext().getFilesDir().getAbsolutePath()+"/ROC/";
                String fileName="test.tmp";
                String filePath2=folderPath+getFileName(folderPath,fileName);
                Log.d("FilePath:",filePath);
                Log.d("FilePath2:",filePath2);
                InputStream in=con.getInputStream();
                OutputStream out=new FileOutputStream(filePath2);
                byte[] data=new byte[2048];
                long total=0;
                int count;
                while((count=in.read(data))!=-1){
                    total+=count;
                    publishProgress((int)total*100/fileLength);
                    out.write(data,0,count);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            myProgressBar.setProgress((int)values[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    /**
     * 名字相同的文件，会在后面加（1）等
     */
    private boolean checkFileExist(String folderPath, String fileName) {
        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            try {
                // 按照指定的路径创建文件夹
                fileDir.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        String filePath = folderPath + fileName;

        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
    private static String getFileName(String folderPath,String fileName){
        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            try {
                // 按照指定的路径创建文件夹
                fileDir.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        File file=new File(folderPath+fileName);
        int i=0;
        while(file.exists()){
            fileName=fileName.replace("("+(i-1)+")", "");
            int dot=fileName.indexOf('.');
            if (dot==-1) {
                dot=fileName.length();
            }
            fileName=fileName.substring(0,dot)+"("+i+")"+fileName.substring(dot,fileName.length());
            i++;
            file=new File(folderPath+fileName);
        }
        return  fileName;
    }
}
