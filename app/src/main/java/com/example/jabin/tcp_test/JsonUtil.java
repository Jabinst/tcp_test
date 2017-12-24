package com.example.jabin.tcp_test;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * json数据转换工具类
 * Created by zhangbbj on 2017/3/28.
 */

public class JsonUtil {

    private static Gson gson = null;

    private static synchronized Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static String toJsonString(Object obj) throws JSONException {
        return getGson().toJson(obj);
    }

    /**
     * 字符串转json数组
     * @param string
     * @return
     * @throws Exception
     */
    public static JSONObject toJsonObj(String string) throws JSONException {
        return new JSONObject(string);
    }
    /**
     * 实体类转json对象
     * @param obj
     * @return
     * @throws Exception
     */
    public static JSONObject toJsonObject(Object obj) throws JSONException {
        return new JSONObject(getGson().toJson(obj));
    }

    /**
     * 字符串转json数组
     * @param string
     * @return
     * @throws Exception
     */
    public static JSONArray toJsonArray(String string) throws JSONException {
        return new JSONArray(string);
    }
    /**
     * 实体类集合转json数组
     * @param collection
     * @return
     * @throws Exception
     */
    public static JSONArray toJsonArray(Collection collection) throws JSONException {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            jsonArray.put(toJsonObject(iterator.next()));
        }
        return jsonArray;
    }

    /**
     * 字符串转实体类
     * @param string
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String string, Class<T> t) throws JSONException {
        if (string == null) {
            return null;
        }
        return getGson().fromJson(string, t);
    }

    /**
     * jsonObject转实体类
     * @param jsonObject
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T fromJson(JSONObject jsonObject, Class<T> t) throws JSONException {
        if (jsonObject == null) {
            return null;
        }
        return fromJson(jsonObject.toString(), t);
    }

    /**
     * jsonArray转实体类List
     * @param jsonArray
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJson(JSONArray jsonArray, Class<T> t) throws JSONException {
        if (jsonArray == null || jsonArray.length() < 1) {
            return null;
        }
        List<T> ts = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            ts.add(fromJson(jsonObject.toString(), t));
        }
        return ts;
    }

    /**
     * Json 转成 Map<>
     * @param jsonObject
     * @return
     */
    public static Map<String, String> toMap(JSONObject jsonObject) {
        try {
            Iterator<String> keyIter= jsonObject.keys();
            String key;
            Map<String, String> valueMap = new HashMap<>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                valueMap.put(key, String.valueOf(jsonObject.get(key)));
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Json 转成 List<Map<>>
     * @param jsonArray
     * @return
     */
    public static List<Map<String, String>> toList(JSONArray jsonArray){
        List<Map<String, String>> list = null;
        try {
            JSONObject jsonObj ;
            list = new ArrayList<>();
            for(int i = 0 ; i < jsonArray.length() ; i ++){
                jsonObj = (JSONObject)jsonArray.get(i);
                list.add(toMap(jsonObj));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }
}