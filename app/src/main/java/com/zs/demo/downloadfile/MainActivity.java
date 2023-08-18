package com.zs.demo.downloadfile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

import java.io.File;

public class MainActivity extends AppCompatActivity {

        private RxPermissions mRxPermission;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRxPermission = new RxPermissions(this);
        WebView myWebView =findViewById(R.id.webhf);
        myWebView.loadUrl("file:///android_asset/index.html");
    }

//    @SuppressLint("CheckResult")
//    public void multipleDownload(View view){
//        mRxPermission.request(Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean){
//                            Intent intent = new Intent(MainActivity.this,MultipleActivity.class);
//                            startActivity(intent);
//                        }else{
//                            Toast.makeText(MainActivity.this , "请打开读写权限" , Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }



}