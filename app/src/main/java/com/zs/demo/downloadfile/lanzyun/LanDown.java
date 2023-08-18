package com.zs.demo.downloadfile.lanzyun;

import com.zs.demo.downloadfile.adapter.DownloadAdapter;
import com.zs.demo.downloadfile.download.DownloadInfo;
import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LanDown {
    public static List<DownloadInfo> mData = new ArrayList<>();
//    List<DownloadInfo> syu= Collections.synchronizedList(mData);
//    public static List<DownloadInfo> mData = new CopyOnWriteArrayList<DownloadInfo>();
    public String  LanOkhttp(String file_id) {
        RequestBody formBody;
        Response response = null;

        String a = null ;
        try {
            if (file_id.equals("-1")) {
                formBody = new FormBody.Builder()
                        .add("task", "5")
                        .add("folder_id", file_id)
                        .add("pg", "1")
                        .add("vei", "BFVUVgNRBAgHBQNTC1E=")
                        .build();
            } else {
                formBody = new FormBody.Builder()
                        .add("task", "22")
                        .add("file_id", file_id)
                        .build();
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://pc.woozooo.com/doupload.php?uid=3289062")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .addHeader("Accept-Encoding", "gzip, deflate, br")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Cookie", "uag=1ef139fa36a6e92191d7b9a9c98a409c; ylogin=3289062;PHPSESSID=19u3e5h9m9ru8s46mo9matmtsnafs24i;phpdisk_info=V2ACMg1nAjcBMwdhAGgDUFQwBA8JYQJiAzhTNFBvUGQAN1RiDGoHM1RvVA1cYwFsVGIFMlw9AmZXMgRiVTUGPVdjAjcNbQI6AWIHZQBpA2lUNwQzCTUCMwMxUzJQZlBjADVUMAxvBzhUYlQ1XA8BalQzBT9cMgJsV2IEZ1VnBjBXawI5")
                    .addHeader("Origin", "https://pc.woozooo.com")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("Pragma", "no-cache")
                    .addHeader("Referer", "https://pc.woozooo.com/account.php?action=login&ref=/mydisk.php")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.188")
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            if (file_id.equals("-1")) {
                JsonDis.jsonData = response.body().string();
            } else {
                a = response.body().string();
                a=lanJson(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 处理json文件列表
     * @param json
     */
    private String lanJson(String  json) {
        JSONObject jsonOb = null;
        String zzx;
        try {
            jsonOb = new JSONObject(json);
            String jsonOb_1 = jsonOb.get("info").toString();
            JSONObject jsonArray = new JSONObject(jsonOb_1);
            zzx = jsonArray.get("is_newd").toString() + "/" + jsonArray.get("f_id").toString();
            System.out.println(zzx);
//            mData.add(new DownloadInfo(zzx));
//            addData(new DownloadInfo(zzx));
            System.out.println("下载完成");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return zzx;
    }
    private final List<Integer> list = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isAdding = false;
    public void addData(DownloadInfo Data){
        try {
            while (isAdding) {
                condition.await();
            }
            isAdding = true;
            mData.add(Data);
            isAdding = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
