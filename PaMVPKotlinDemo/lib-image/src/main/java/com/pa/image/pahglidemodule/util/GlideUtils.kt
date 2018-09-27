package com.pa.image.pahglidemodule.util

import android.content.Context
import android.text.TextUtils

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.pa.image.pahglidemodule.progress.GlideApp

/**
 * Glide工具类
 */
class GlideUtils private constructor() {

    /**
     * 初始化Glide
     *
     * @param context
     */
    fun initGlide(context: Context?, cacheURL: String?, diskCacheSizeBytes: Int, memorySize: Int) {

        val builder = GlideBuilder()
        //磁盘缓存配置（默认缓存大小250M，默认保存在内部存储中）
        //设置磁盘缓存保存在外部存储，且指定缓存大小
        if (!TextUtils.isEmpty(cacheURL) && diskCacheSizeBytes > 0)
            builder.setDiskCache(ExternalCacheDiskCacheFactory(context, cacheURL, diskCacheSizeBytes))
        //设置内存缓存
        if (memorySize > 0) {
            builder.setMemoryCache(LruResourceCache(memorySize.toLong()))
        }
        GlideApp.init(context!!, builder)
    }

    companion object {

        private var mContext: Context? = null
        @Volatile private var inst: GlideUtils? = null

        val instance: GlideUtils?
            get() {
                if (inst == null) {
                    synchronized(GlideUtils::class.java) {
                        if (inst == null) {
                            inst = GlideUtils()
                        }
                    }
                }
                return inst
            }
    }
}
