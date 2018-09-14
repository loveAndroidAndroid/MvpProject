package com.pa.image.pahglidemodule.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;
import com.pa.image.pahglidemodule.util.BlurUtils;

import java.security.MessageDigest;

/**
 * 画模糊图
 */
public class BlurTransformation extends BitmapTransformation {
    private final String TAG = getClass().getName();

    private static int MAX_RADIUS = 25;
    private static int DEFAULT_SAMPLING = 1;

    private Context context;
    private int radius; //模糊半径0～25
    private int sampling; //取样0～25

    public BlurTransformation(Context context) {
        this(context, MAX_RADIUS, DEFAULT_SAMPLING);
    }

    public BlurTransformation(Context context, int radius) {
        this(context, radius, DEFAULT_SAMPLING);
    }

    public BlurTransformation(Context context, int radius, int sampling) {
        this.context = context;
        this.radius = radius > MAX_RADIUS ? MAX_RADIUS : radius;
        this.sampling = sampling > MAX_RADIUS ? MAX_RADIUS : sampling;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;

        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        //使位图过滤的位掩码标志 同抗锯齿
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);
        //模糊处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            bitmap = BlurUtils.rsBlur(context, bitmap, radius);
        } else {
            bitmap = BlurUtils.blur(bitmap, radius);
        }
        return bitmap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlurTransformation) {
            BlurTransformation other = (BlurTransformation) obj;
            return radius == other.radius && sampling == other.sampling;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(TAG.hashCode(), Util.hashCode(radius, Util.hashCode(sampling)));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((TAG + radius * 10 + sampling).getBytes(CHARSET));
    }
}
