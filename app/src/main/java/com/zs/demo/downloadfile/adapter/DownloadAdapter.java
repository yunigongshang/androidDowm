package com.zs.demo.downloadfile.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import android.widget.TextView;
import com.zs.demo.downloadfile.R;
import com.zs.demo.downloadfile.download.DownloadInfo;
import com.zs.demo.downloadfile.download.DownloadManager;
import com.zs.demo.downloadfile.lanzyun.LanOkhttp;
import com.zs.demo.downloadfile.lanzyun.ThreadLan;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

/**
 * Created by zs
 * Date：2018年 09月 11日
 * Time：18:06
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.UploadHolder>  {

    private List<DownloadInfo> mdata;

    private List<String > lan_name;

    public DownloadAdapter(List<DownloadInfo> mdata, List<String > lan_name) {
        this.lan_name = lan_name;
        this.mdata = mdata;
    }

    /**
     * 更新下载进度
     * @param info
     */
    public void updateProgress(DownloadInfo info){
        for (int i = 0; i < mdata.size(); i++){
            if (mdata.get(i).getUrl().equals(info.getUrl())){
                mdata.set(i,info);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public UploadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_download_layout,null);
        return new UploadHolder(view);
    }

    @Override
    public void onBindViewHolder(UploadHolder holder, int position) {
        holder.fin_name.setText(lan_name.get(position));
        final DownloadInfo info = mdata.get(position);
        if (DownloadInfo.DOWNLOAD_CANCEL.equals(info.getDownloadStatus())){
            holder.main_progress.setProgress(0);
        }else if (DownloadInfo.DOWNLOAD_OVER.equals(info.getDownloadStatus())){
            holder.main_progress.setProgress(holder.main_progress.getMax());
        }else {
            if (info.getTotal() == 0){
                holder.main_progress.setProgress(0);
            }else {
                holder.main_progress.setProgress((int) (info.getProgress() * holder.main_progress.getMax() / info.getTotal()));
                holder.main_progress.setMax((int) info.getTotal());
                float d = info.getProgress() * holder.main_progress.getMax() / info.getTotal();
                float progress= info.getProgress();//已下载的大小
                long total= info.getTotal();//文件总大小
                float f= (progress/total)*100;//已下的百分比
                String pe=(int)f+"%";
                holder.main_progress.setProgress((int) d);
                holder.tv_progress.setText(pe);
                if (pe.equals("100%")||info.getProgress()==info.getTotal()) {

                }

            }
        }

        holder.main_btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                if (holder.main_btn_down.getText().toString().equals("下载")){
                    ThreadLan.MyThread3 myThread3=new ThreadLan.MyThread3();
                    ThreadLan.file_id=info.getUrl();
                    myThread3.start();
                    try {
                        myThread3.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    for (int i=0;i<mdata.size();i++){
                        if (mdata.get(i).getUrl().equals(ThreadLan.file_id)){
                            mdata.set(i,new DownloadInfo(ThreadLan.url_id));
                            break;
                        }
                    }
                    DownloadManager.getInstance().download(ThreadLan.url_id);

//                DownloadManager.getInstance().download(info.getUrl());
                holder.main_btn_down.setText("取消");
                }else{
                    DownloadManager.getInstance().cancelDownload(info);
                    holder.tv_progress.setText(null);
                    holder.main_btn_down.setText("下载");
                }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class UploadHolder extends RecyclerView.ViewHolder{

        private ProgressBar main_progress;
        private Button main_btn_down;
        private final TextView tv_progress;
        private  TextView fin_name;

        public UploadHolder(View itemView) {
            super(itemView);
            main_progress = itemView.findViewById(R.id.main_progress);
            main_btn_down = itemView.findViewById(R.id.main_btn_down);
            tv_progress = itemView.findViewById(R.id.tv_progress);
            fin_name=itemView.findViewById(R.id.fin_name);
        }
    }

}
