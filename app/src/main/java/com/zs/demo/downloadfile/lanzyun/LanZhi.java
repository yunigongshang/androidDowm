package com.zs.demo.downloadfile.lanzyun;

import com.zs.demo.downloadfile.download.DownloadManager;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanZhi {
    private  String sgin="";
    private  String lanzhi=null;
    private  String pwd=null;
    public String downZhiJson(String jsonZhi){
        try {
            JSONObject jsonOb = new JSONObject(jsonZhi);
            String jsonOb_1=jsonOb.get("info").toString();
            JSONObject jsonArray = new JSONObject(jsonOb_1);
            lanzhi= jsonArray.get("is_newd").toString()+"/"+jsonArray.get("f_id").toString();
            pwd=jsonArray.get("pwd").toString();
            downzhi(lanzhi);
            sgin=getFileNameFromUrl(sgin);//获取sign值
            lanzhi=phpPost(sgin,pwd);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return lanzhi;
    }
    public void downzhi(String url){//获取直链的请求参数
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Pragma", "no-cache")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.203")
                    .build();

            Response response = client.newCall(request).execute();
            sgin = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取sign值
     * @param url
     * @return 返回sign值
     */

    public static String getFileNameFromUrl(String url) {
        // 编写正则表达式
        String regFileName = "(?<=var skdklds = ')(.*?)(?=';)";//只需要sign的值
        // 匹配当前正则表达式
        Matcher matcher = Pattern.compile(regFileName).matcher(url);
//        // 定义当前文件的文件名称
        String fileName = "";
        // 判断是否可以找到匹配正则表达式的字符
        if (matcher.find()) {
            // 将匹配当前正则表达式的字符串即文件名称进行赋值
            fileName = matcher.group();
        }
        // 返回
//        fileName=fileName.substring(5);

        return fileName;
    }

    /**
     * 获取下载链接，重定向获取真正的下载链接
     * @param sgin  sgin值
     * @param pwd   访问密码
     * @return 返回直链下载地址
     */
    public String phpPost(String sgin, String pwd) {
        String url_zhi = null;
        String jsonOb_1 = null;
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("action", "downprocess")
                    .add("sign", sgin)
                    .add("p", pwd)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://wwp.lanzoup.com/ajaxm.php")
                    .method("POST", formBody)
                    .header("Accept", "application/json, text/javascript, */*")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                    .addHeader("Accept-Encoding", "gzip, deflate, br")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Content-Length","111")
                    .addHeader("Cookie", "codelen=1; Hm_lvt_fb7e760e987871d56396999d288238a4=1676563278; uz_distinctid=1865af5695edf8-021e36e76dddb4-74525471-186a00-1865af5695f10cd; pc_ad1=1")
                    .addHeader("Host", "wwp.lanzoup.com")
                    .addHeader("Origin", "https://wwp.lanzoup.com")
                    .addHeader("Pragma", "no-cache")
                    .addHeader("Referer", lanzhi)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.203")
                    .build();

            Response response = client.newCall(request).execute();
            url_zhi = response.body().string();
            JSONObject jsonOb = new JSONObject(url_zhi);
            jsonOb_1 = jsonOb.get("dom") +"/file/"+ jsonOb.get("url").toString();
            DownloadManager.fileName_newid = jsonOb.get("inf").toString();




            OkHttpClient client2 = new OkHttpClient().newBuilder()
                    .followRedirects(false)
                    .build();

            Request request2 = new Request.Builder()
                    .url(jsonOb_1)
                    .method("GET",null)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                    .addHeader("Cookie","down_ip=1; expires=Sat, 16-Nov-2019 11:42:54 GMT; path=/; domain=.baidupan.com")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36")
                    .build();
            Response response2 = client2.newCall(request2).execute();
            String b=response2.body().string();
            System.out.println(b);
            String a=response2.headers().get("Location");
            jsonOb_1=a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonOb_1;
    }

}
