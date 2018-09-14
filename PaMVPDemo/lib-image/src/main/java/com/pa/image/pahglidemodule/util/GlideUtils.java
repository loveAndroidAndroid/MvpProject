package com.pa.image.pahglidemodule.util;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.pa.image.pahglidemodule.progress.GlideApp;

/**
 * Glide工具类
 */
public class GlideUtils {

    private static Context mContext;
    private volatile static GlideUtils inst;

    private GlideUtils() {

    }

    public static GlideUtils getInstance() {
        if (inst == null) {
            synchronized (GlideUtils.class) {
                if (inst == null) {
                    inst = new GlideUtils();
                }
            }
        }
        return inst;
    }

    /**
     * 初始化Glide
     *
     * @param context
     */
    public void initGlide(Context context,String cacheURL,int diskCacheSizeBytes,int memorySize) {

        this.mContext = context;
        GlideBuilder builder = new GlideBuilder();
        //磁盘缓存配置（默认缓存大小250M，默认保存在内部存储中）
        //设置磁盘缓存保存在外部存储，且指定缓存大小
        if (!TextUtils.isEmpty(cacheURL)&&diskCacheSizeBytes>0)
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheURL, diskCacheSizeBytes));
        //设置内存缓存
        if (memorySize >0){
            builder.setMemoryCache(new LruResourceCache(memorySize));
        }
        GlideApp.init(context, builder);
    }
}
