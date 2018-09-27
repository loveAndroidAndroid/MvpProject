package com.pa.image.pahglidemodule.transformation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.support.annotation.RequiresApi
import com.bumptech.glide.load.Key

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import com.pa.image.pahglidemodule.util.BlurUtils

import java.security.MessageDigest

/**
 * 画模糊图
 */
class BlurTransformation @JvmOverloads constructor(private val context: Context, radius: Int = MAX_RADIUS, sampling: Int = DEFAULT_SAMPLING) : BitmapTransformation() {
    private val TAG = javaClass.getName()
    private val radius: Int //模糊半径0～25
    private val sampling: Int //取样0～25

    init {
        this.radius = if (radius > MAX_RADIUS) MAX_RADIUS else radius
        this.sampling = if (sampling > MAX_RADIUS) MAX_RADIUS else sampling
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        val width = toTransform.width
        val height = toTransform.height
        val scaledWidth = width / sampling
        val scaledHeight = height / sampling

        var bitmap: Bitmap? = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap!!)
        canvas.scale(1 / sampling.toFloat(), 1 / sampling.toFloat())
        val paint = Paint()
        //使位图过滤的位掩码标志 同抗锯齿
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        //模糊处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            bitmap = BlurUtils.rsBlur(context, bitmap, radius)
        } else {
            bitmap = BlurUtils.blur(bitmap, radius)
        }
        return bitmap
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is BlurTransformation) {
            val other = obj as BlurTransformation?
            return radius == other!!.radius && sampling == other.sampling
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(TAG.hashCode(), Util.hashCode(radius, Util.hashCode(sampling)))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((TAG + radius * 10 + sampling).toByteArray(Key.CHARSET))
    }

    companion object {

        private val MAX_RADIUS = 25
        private val DEFAULT_SAMPLING = 1
    }
}
