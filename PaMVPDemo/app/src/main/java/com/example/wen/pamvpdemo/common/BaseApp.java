package com.example.wen.pamvpdemo.common;

import android.app.Application;
import android.content.Context;

import com.example.wen.pamvpdemo.http.ApiUrls;
import com.pa.core.config.GlobalConfigManager;
import com.pa.core.config.ImageLoaderConfig;
import com.pa.core.config.NetworkConfig;
import com.pa.core.nethelper.AddCookiesInterceptor;
import com.pa.core.nethelper.HttpLoggingInterceptor;
import com.pa.core.nethelper.ReceivedCookiesInterceptor;

import okhttp3.Interceptor;

/**
 * Created by wen on 2018/5/14.
 */

public class BaseApp extends Application {

    //全局上下文
    private static volatile Context sContext = null;

    public static synchronized Context getContext() {
        return sContext;
    }

    Interceptor[] interceptors = new Interceptor[]{new HttpLoggingInterceptor(HttpLoggingInterceptor.Level.BODY), new ReceivedCookiesInterceptor(this), new AddCookiesInterceptor(this)};

    /**
     * 图片缓存路径
     */
    public static final String CACHE_PHOTO = "Photo";

    @Override
    public void onCreate() {
        super.onCreate();
        //全局上下文
        sContext = this;

        //必须传入baseUrl readtime  writetime connecttime 默认为30
        //拦截器默认为HttpLoggingInterceptor 可通过setInterceptor 添加
        NetworkConfig networkConfig = new NetworkConfig
                .Builder()
                .setBaseURL(ApiUrls.BaseUrl)
                .setReadTime(20)
                .setWriteTime(20)
                .setConnectTime(20)
                .setInterceptor(interceptors)
                .build();

        ImageLoaderConfig imageLoaderConfig = new ImageLoaderConfig.Builder()
                .setCacheUrl(CACHE_PHOTO)
                .setDiskCacheSizeBytes(1024 * 1024 * 250)
                .setMemorySize(1024 * 1024 * 100)
                .setContexts(this)
                .build();


        GlobalConfigManager.initNet(networkConfig);
        GlobalConfigManager.initImageLoader(imageLoaderConfig);
    }
}
