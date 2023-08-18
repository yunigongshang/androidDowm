package com.zs.demo.downloadfile;

import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zs
 * Date：2018年 09月 12日
 * Time：13:54
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class Constant {

    /**
     * 下载路径
     */
    public final static String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AAA/";

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        File file = new File(FILE_PATH + fileName);
        if (file.exists()){
            checker.checkDelete(file.toString());
            if (file.isFile()) {
                try {
                    file.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        }else
            status = false;
        return status;
    }

}
