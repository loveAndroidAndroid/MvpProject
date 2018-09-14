package com.pa.core.imagehelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.pa.core.config.ImageLoaderConfig;
import com.pa.image.pahglidemodule.progress.GlideApp;
import com.pa.image.pahglidemodule.progress.GlideRequest;
import com.pa.image.pahglidemodule.progress.OnProgressListener;
import com.pa.image.pahglidemodule.progress.ProgressManager;
import com.pa.image.pahglidemodule.transformation.CircleTransformation;
import com.pa.image.pahglidemodule.transformation.RadiusTransformation;
import com.pa.image.pahglidemodule.util.GlideCacheUtil;
import com.pa.image.pahglidemodule.util.GlideUtils;

/**
 * Glide工具类
 */
public class GlideManger {

    private static Context mContext;
    private volatile static GlideManger inst;

    private static GlideRequest<Drawable> glideRequest;

    private GlideManger() {

    }

    public static GlideManger getInstance() {
        if (inst == null) {
            synchronized (GlideManger.class) {
                if (inst == null) {
                    inst = new GlideManger();
                }
            }
        }
        return inst;
    }

    public void init(ImageLoaderConfig imageLoaderConfig){
        mContext = imageLoaderConfig.context;
        GlideUtils.getInstance().initGlide(imageLoaderConfig.context,imageLoaderConfig.cacheUrl,imageLoaderConfig.diskCacheSizeBytes,imageLoaderConfig.memorySize);
    }

    public void setImage(String url, ImageView imageView, int holderId) {
        glideRequest = GlideApp.with(mContext).asDrawable();
        glideRequest.load(url);
        if (holderId != 0) {
            glideRequest = glideRequest.placeholder(holderId);
        }
        glideRequest.into(new GlideImageViewTarget(imageView, url));
    }

    public void setImage(String url, ImageView imageView, int holderId,OnProgressListener onProgressListener) {
        ProgressManager.addListener(url, onProgressListener);
        glideRequest = GlideApp.with(mContext).asDrawable();
        glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop().load(url);
        if (holderId != 0) {
            glideRequest = glideRequest.placeholder(holderId);
        }
        glideRequest.into(new GlideImageViewTarget(imageView, url));
    }

    public void setImageRound(String url, ImageView imageView, int holderId, boolean roundCorner) {
        glideRequest = GlideApp.with(mContext).asDrawable();
        glideRequest.load(url);
        if (holderId != 0) {
            glideRequest = glideRequest.placeholder(holderId);
        }
        if (roundCorner) {
            glideRequest = glideRequest.transform(new RadiusTransformation(mContext, 10));
        }
        glideRequest.into(new GlideImageViewTarget(imageView, url));
    }

    public void setImageCircle(String url, ImageView imageView, int holderId) {
        glideRequest = GlideApp.with(mContext).asDrawable();
        glideRequest.load(url);
        if (holderId != 0) {
            glideRequest = glideRequest.placeholder(holderId);
        }
        glideRequest = glideRequest.transform(new CircleTransformation());

        glideRequest.into(new GlideImageViewTarget(imageView, url));
    }

    /**
     * 自定义DrawableImageViewTarget 接受回调
     */
    private class GlideImageViewTarget extends DrawableImageViewTarget {

        private String mUrl;

        GlideImageViewTarget(ImageView view, String url) {
            super(view);
            this.mUrl = url;
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            super.onLoadStarted(placeholder);
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            OnProgressListener onProgressListener = ProgressManager.getProgressListener(mUrl);
            if (onProgressListener != null) {
                onProgressListener.onProgress(true, 100, 0, 0);
                ProgressManager.removeListener(mUrl);
            }
            super.onLoadFailed(errorDrawable);
        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            OnProgressListener onProgressListener = ProgressManager.getProgressListener(mUrl);
            if (onProgressListener != null) {
                onProgressListener.onProgress(true, 100, 0, 0);
                ProgressManager.removeListener(mUrl);
            }
            super.onResourceReady(resource, transition);
        }
    }

    /**
     * 给VIew设置背景
     */
    public void setBitmap(Activity activity, String url, View imageView) {
        GlideApp.with(activity).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                imageView.setBackground(new dr);
            }
        });
    }

    public RequestOptions getRequestOptions() {
        RequestOptions options = new RequestOptions()
                .placeholder(android.R.color.holo_red_dark)    //加载成功之前占位图
                .error(android.R.color.holo_red_dark)    //加载错误之后的错误图
                .override(400, 400)    //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
                .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                .format(DecodeFormat.DEFAULT)
                .skipMemoryCache(true)    //跳过内存缓存
                .transform(new CircleTransformation())//设置裁剪方式
                .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
                .diskCacheStrategy(DiskCacheStrategy.NONE)    //跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.DATA)    //只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片

        return options;
    }


    /**
     * 清除图片磁盘缓存，只能在子线程中执行
     */
    public void clearImageDiskCache(final Context context) {
        try {
            GlideCacheUtil.getInstance().clearImageDiskCache(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存，只能在主线程执行
     * 只能在主线程执行
     */
    public void clearImageMemoryCache(Context context) {
        try {
            GlideCacheUtil.getInstance().clearImageMemoryCache(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        try {
            GlideCacheUtil.getInstance().clearImageAllCache(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
