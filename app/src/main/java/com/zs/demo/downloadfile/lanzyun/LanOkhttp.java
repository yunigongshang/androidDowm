package com.zs.demo.downloadfile.lanzyun;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class LanOkhttp {
//    public static String file_id=null;
    RequestBody formBody;

    /**
     * 获取蓝奏云的文件列表
     * @param file_id file_id=-1是文件列表，数字串是文件id
     * @return
     * @throws IOException
     */
    public String Ohget(String file_id) throws IOException {
        Response response = null;
        String abb = null;
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
                JsonDis jsonDis=new JsonDis();
//                jsonDis.analyzeJSONArray1();
                abb = JsonDis.jsonData;
            }else{
                LanZhi lanZhi=new LanZhi();
                abb=response.body().string();
                abb=lanZhi.downZhiJson(abb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return abb;
    }
}

