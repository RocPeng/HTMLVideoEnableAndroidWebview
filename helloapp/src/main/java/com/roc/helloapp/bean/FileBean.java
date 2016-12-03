package com.roc.helloapp.bean;

import com.roc.helloapp.util.FileUtil;
import com.roc.helloapp.util.IoUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ROC on 2016/11/23.
 */

public class FileBean {
    private String absolutePath;
    private String fileName;
    private String fileLength;
    private String fileDate;
    private String fileState;
    private String url;
    public static final String NOTDOWNLOAD="-1";
    public static final String ISDOWNLOADING="0";
    public static final String DOWNLOADSUCCESS="1";
    public static final String DOWNLOADFAIL="2";
    public void setFilePath(String absolutePath){
        this.absolutePath=absolutePath;
        this.fileName= FileUtil.getFileName(absolutePath);
        File file=new File(absolutePath);
        long length=0;
        try {
            length = IoUtil.getFileSizes(file);
            length /= 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.fileLength=length+"KB";
        this.fileDate=getDateTime(file.lastModified());
        if(!file.exists()){
            fileState=NOTDOWNLOAD;
        }
        if(FileUtil.getFileExtension(absolutePath).equals("tmp")){
            fileState=DOWNLOADFAIL;
        }else{
            fileState=DOWNLOADSUCCESS;
        }
    }
    public void setFileState(String fileState){
        this.fileState=fileState;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public String getFileAbsolutePath(){
        return this.absolutePath;
    }
    public String getFileName(){
        return this.fileName;
    }
    public String getFileLength(){
        return this.fileLength;
    }
    public String getFileDate(){
        return this.fileDate;
    }
    public String getFileState(){
        return this.fileState;
    }
    public String getFileUrl(){
        return this.url;
    }

    public static String getDateTime(long time) {
        Date date=new Date(time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = timeFormat.format(date);
        return currentTime;
    }

}
