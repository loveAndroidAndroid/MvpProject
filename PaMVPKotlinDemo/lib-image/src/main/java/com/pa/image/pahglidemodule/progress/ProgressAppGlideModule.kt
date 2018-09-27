package com.pa.image.pahglidemodule.progress

import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule

import java.io.InputStream

/**
 * 注册进度管理者
 * https://blog.csdn.net/guolin_blog/article/details/78357251  3.7版本
 */
@GlideModule
class ProgressAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java!!, InputStream::class.java!!, OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()!!))
    }
}