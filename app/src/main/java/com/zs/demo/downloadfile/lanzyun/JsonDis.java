package com.zs.demo.downloadfile.lanzyun;

import android.view.View;
import com.zs.demo.downloadfile.download.DownloadInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * JSON数组处理
 */
public class JsonDis {
    protected static String jsonData;
    public static List<String> lan_id= new ArrayList<>();
    public List<String > lan_name = new ArrayList<>();
    private List<String> lan_size= new ArrayList<>();

    public static List<DownloadInfo> mData = new ArrayList<>();

    public void analyzeJSONArray1() {
        //解析json数组
        JSONArray jsonArray = null;
        try {
            JSONObject jsonOb = new JSONObject(jsonData);
            String jsonOb_1=jsonOb.get("text").toString();
            jsonArray = new JSONArray(jsonOb_1);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mData.add((new DownloadInfo(jsonObject.optString("id", null))));
                lan_id.add(jsonObject.optString("id",null));
                lan_size.add(jsonObject.optString("size", null));
                lan_name.add(jsonObject.optString("name", null));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
