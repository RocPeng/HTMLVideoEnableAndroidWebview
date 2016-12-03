package com.roc.helloapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.roc.helloapp.R;
import com.roc.helloapp.bean.FileBean;
import com.roc.helloapp.util.FileUtil;

import java.util.LinkedList;
import java.util.List;

public class DownLoadListActivity extends AppCompatActivity {
    private List<String> fileDirList;
    private List<FileBean> fileList;
    private List<FileBean> downloadList;
    private String fileDir;
    private static final String TAG = "DownLoadListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_list);
        //初始化数据
        fileDirList=new LinkedList<>();
        fileList=new LinkedList<>();
        downloadList=new LinkedList<>();
        fileDir=getApplicationContext().getFilesDir().getAbsolutePath()+"/ROC/";
    }

    @Override
    protected void onStart() {
        super.onStart();
        //生成数据
        FileUtil.getFiles(fileDir,fileDirList);
        for(String fileDirPath:fileDirList){
            Log.d(TAG,fileDirPath);
            FileBean fileBean=new FileBean();
            fileBean.setFilePath(fileDirPath);
            fileList.add(fileBean);
        }
        for( FileBean fileBean:fileList){
            Log.d(TAG,fileBean.getFileDate());
            Log.d(TAG,fileBean.getFileLength());
            Log.d(TAG,fileBean.getFileName());
            Log.d(TAG,fileBean.getFileState());
        }
    }
}
