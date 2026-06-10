package com.yellowbee.gesturetools.utils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * @author xuaya
 * @date 2023/10/18
 * @description Json工具
 * @since
 */
public class JsonUtils {

    private static final String TAG = "JsonUtils";

    private static final Gson gson = new Gson();

    /**
     * 将 JSON 字符串转换为 JSONObject
     * @param jsonString
     * @return
     */
    public static JSONObject parseJsonObject(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * // 将 JSON 字符串转换为 JSONArray
     * @param jsonString
     * @return
     */
    public static JSONArray parseJsonArray(String jsonString) {
        try {
            return new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转换为 JSON 字符串
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 将 JSON 字符串转换为对象
     * @param jsonString
     * @param classOfT
     * @return
     * @param <T>
     */
    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        try {
            return gson.fromJson(jsonString, classOfT);
        } catch (Exception e) {
           MyLog.e(TAG, "fromJson parse error: " +e.getMessage());
            return null;
        }
    }

    /**
     * 将 JSON 字符串转换为 JsonObject
     * @param jsonString
     * @return
     */
    public static JsonObject parseJsonObjectWithGson(String jsonString) {
        try {
            JsonElement jsonElement = JsonParser.parseString(jsonString);
            if (jsonElement.isJsonObject()) {
                return jsonElement.getAsJsonObject();
            }
        } catch (JsonParseException e) {
            MyLog.e(TAG, "parseJsonObjectWithGson parse error: " +e.getMessage());
        }
        return null;
    }

    /**
     * 将 JSON 字符串转换为对象
     * @param jsonString
     * @param typeOfT
     * @return
     * @param <T>
     */
    public static <T> T fromJson(String jsonString, Type typeOfT) {
        try {
            return gson.fromJson(jsonString, typeOfT) ;
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(e.getMessage());
            return null;
        }
    }



}

