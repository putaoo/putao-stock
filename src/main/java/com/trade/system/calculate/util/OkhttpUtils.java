package com.trade.system.calculate.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description
 * Created by putao on  2019/1/4 11:21
 **/
public class OkhttpUtils {

    public static MediaType mediaType = MediaType.parse("application/json");

    private static OkHttpClient client;

    public static OkHttpClient getClient () {
        if ( client == null ) {
            synchronized ( OkhttpUtils.class ) {
                if ( client == null ) {
                    ConnectionPool pool = new ConnectionPool(5000 , 10 , TimeUnit.MINUTES);
                    client = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .readTimeout(5 , TimeUnit.MINUTES)
                            .writeTimeout(5 , TimeUnit.MINUTES)
                            .connectionPool(pool)
                            .connectTimeout(5 , TimeUnit.MINUTES).build();
                }
            }
        }
        return client;
    }

    public static Document post ( Map<String, Object> paramsMap , String url , Map<String, String> header ) {
        try {
            RequestBody requestBody = RequestBody.create(mediaType , JSON.toJSONString(paramsMap));
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .headers(Headers.of(header))
                    .build();
            Response response = getClient().newCall(request).execute();
            return Jsoup.parse(response.body().string());
        } catch ( IOException e ) {
            throw new RuntimeException("连接错误");
        }
    }

    public static Document get ( String url , Map<String, String> header ) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .headers(Headers.of(header))
                    .build();
            Response response = getClient().newCall(request).execute();
            return Jsoup.parse(response.body().string());
        } catch ( IOException e ) {
            throw new RuntimeException("连接错误");
        }
    }
}
