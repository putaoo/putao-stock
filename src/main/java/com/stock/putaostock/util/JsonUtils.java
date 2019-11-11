//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.stock.putaostock.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static ValueFilter filter = (obj, s, v) -> {
        return v != null && !"null".equals(v) ? v : "";
    };

    public JsonUtils() {
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    public static JSONObject parseObject(String json) {
        return JSON.parseObject(json);
    }

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj, filter, new SerializerFeature[]{SerializerFeature.WriteMapNullValue});
    }

    public static String toJSONString2(Object obj) {
        return JSON.toJSONString(obj, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNonStringKeyAsString});
    }

    public static void main(String[] args) {
        Map<Object, Object> map = new HashMap();
        map.put(11, "123");
        map.put("b", (Object)null);
        System.out.println(toJSONString2(map));
    }
}
