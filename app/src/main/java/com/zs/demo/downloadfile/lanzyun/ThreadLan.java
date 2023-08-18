package com.zs.demo.downloadfile.lanzyun;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class ThreadLan extends AppCompatActivity {
    public static String file_id=null;
    public static String url_id=null;
    static LanOkhttp lanOkhttp=new LanOkhttp();

    public static class MyThread3 extends Thread{
        @Override
        public void run() {
            try {
                if (file_id.matches("^[0-9]*$") || file_id.equals("-1")) {
                    url_id = lanOkhttp.Ohget(file_id);
                }
            }
            catch (Exception e){
                e.printStackTrace();

            }
        }
    }
    public static class MyThread2 extends Thread{
        @Override
        public void run() {
            try {
                LanDown lan=new LanDown();
                lan.LanOkhttp(JsonDis.lan_id.get(8));
            }
            catch (Exception e){
                e.printStackTrace();

            }
        }
    }
}
