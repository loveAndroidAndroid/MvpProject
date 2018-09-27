package com.pa.core.imagehelper

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.pa.core.config.ImageLoaderConfig
import com.pa.image.pahglidemodule.progress.GlideApp
import com.pa.image.pahglidemodule.progress.GlideRequest
import com.pa.image.pahglidemodule.progress.OnProgressListener
import com.pa.image.pahglidemodule.progress.ProgressManager
import com.pa.image.pahglidemodule.transformation.CircleTransformation
import com.pa.image.pahglidemodule.transformation.RadiusTransformation
import com.pa.image.pahglidemodule.util.GlideCacheUtil
import com.pa.image.pahglidemodule.util.GlideUtils

/**
 * Glide工具类
 */
class GlideManger {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null

        @SuppressLint("StaticFieldLeak")
        @Volatile private var inst: GlideManger? = null

        @SuppressLint("StaticFieldLeak")
        private var glideRequest: GlideRequest<Drawable>? = null

        //如果 ?: 左侧表达式非空，就返回其左侧表达式，否则返回右侧表达式
        //it指代当前对象  also把创建的BaseApp()对象赋值给it
        fun getInstance(): GlideManger {
            return inst ?: synchronized(this) {
                inst ?: GlideManger().also { inst = it }
            }
        }
    }

    //加载成功之前占位图
    //加载错误之后的错误图
    //指定图片的尺寸
    //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
    //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
    //指定图片的缩放类型为centerCrop （圆形）
    //跳过内存缓存
    //设置裁剪方式
    //缓存所有版本的图像
    //跳过磁盘缓存
    //只缓存原来分辨率的图片
    //只缓存最终的图片
    val requestOptions: RequestOptions
        get() = RequestOptions()
                .placeholder(R.color.holo_red_dark)
                .error(R.color.holo_red_dark)
                .override(400, 400)
                .fitCenter()
                .centerCrop()
                .circleCrop()
                .format(DecodeFormat.DEFAULT)
                .skipMemoryCache(true)
                .transform(CircleTransformation())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

    fun init(imageLoaderConfig: ImageLoaderConfig) {
        mContext = imageLoaderConfig.context
        GlideUtils.instance?.initGlide(imageLoaderConfig.context, imageLoaderConfig.cacheUrl, imageLoaderConfig.diskCacheSizeBytes, imageLoaderConfig.memorySize)
    }

    fun setImage(url: String, imageView: ImageView, holderId: Int) {
        glideRequest = GlideApp.with(mContext!!).asDrawable()
        glideRequest?.load(url)
        if (holderId != 0) {
            glideRequest = glideRequest?.placeholder(holderId)
        }
        glideRequest?.into(GlideImageViewTarget(imageView, url))
    }

    fun setImage(url: String, imageView: ImageView, holderId: Int, onProgressListener: OnProgressListener) {
        ProgressManager.addListener(url, onProgressListener)
        glideRequest = GlideApp.with(mContext!!).asDrawable()
        glideRequest?.diskCacheStrategy(DiskCacheStrategy.NONE)?.centerCrop()?.load(url)
        if (holderId != 0) {
            glideRequest = glideRequest?.placeholder(holderId)
        }
        glideRequest?.into(GlideImageViewTarget(imageView, url))
    }

    fun setImageRound(url: String, imageView: ImageView, holderId: Int, roundCorner: Boolean) {
        glideRequest = GlideApp.with(mContext!!).asDrawable()
        glideRequest?.load(url)
        if (holderId != 0) {
            glideRequest = glideRequest?.placeholder(holderId)
        }
        if (roundCorner) {
            glideRequest = glideRequest?.transform(RadiusTransformation(mContext!!, 10))
        }
        glideRequest?.into(GlideImageViewTarget(imageView, url))
    }

    fun setImageCircle(url: String, imageView: ImageView, holderId: Int) {
        glideRequest = GlideApp.with(mContext!!).asDrawable()
        glideRequest?.load(url)
        if (holderId != 0) {
            glideRequest = glideRequest?.placeholder(holderId)
        }
        glideRequest = glideRequest?.transform(CircleTransformation())

        glideRequest?.into(GlideImageViewTarget(imageView, url))
    }

    /**
     * 自定义DrawableImageViewTarget 接受回调
     */
    private inner class GlideImageViewTarget internal constructor(view: ImageView, private val mUrl: String) : DrawableImageViewTarget(view) {

        override fun onLoadStarted(placeholder: Drawable?) {
            super.onLoadStarted(placeholder)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            val onProgressListener = ProgressManager.getProgressListener(mUrl)
            if (onProgressListener != null) {
                onProgressListener.onProgress(true, 100, 0, 0)
                ProgressManager.removeListener(mUrl)
            }
            super.onLoadFailed(errorDrawable)
        }

        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            val onProgressListener = ProgressManager.getProgressListener(mUrl)
            if (onProgressListener != null) {
                onProgressListener.onProgress(true, 100, 0, 0)
                ProgressManager.removeListener(mUrl)
            }
            super.onResourceReady(resource, transition)
        }
    }

    /**
     * 给VIew设置背景
     */
    fun setBitmap(activity: Activity, url: String, imageView: View) {
        GlideApp.with(activity).asBitmap().load(url).into(object : SimpleTarget<Bitmap>() {

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                //                imageView.setBackground(new dr);
            }
        })
    }


    /**
     * 清除图片磁盘缓存，只能在子线程中执行
     */
    fun clearImageDiskCache(context: Context) {
        try {
            GlideCacheUtil.instance?.clearImageDiskCache(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 清除图片内存缓存，只能在主线程执行
     * 只能在主线程执行
     */
    fun clearImageMemoryCache(context: Context) {
        try {
            GlideCacheUtil.instance?.clearImageMemoryCache(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 清除图片所有缓存
     */
    fun clearImageAllCache(context: Context) {
        try {
            GlideCacheUtil.instance?.clearImageAllCache(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
