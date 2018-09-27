package com.pa.image.pahglidemodule.transformation

import android.graphics.*
import com.bumptech.glide.load.Key

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util

import java.security.MessageDigest

/**
 * 裁剪图片为圆
 */
class CircleTransformation : BitmapTransformation() {
    private val TAG = javaClass.getName()

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        //得到图片最小边
        val size = Math.min(toTransform.width, toTransform.height)
        //计算图片起点
        val x = (toTransform.width - size) / 2
        val y = (toTransform.height - size) / 2
        //创建新的bitmaop
        val square = Bitmap.createBitmap(toTransform, x, y, size, size)
        //得到glide中BitmapPool的bitmap位图对象
        val circle = pool.get(size, size, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(circle)
        val paint = Paint()
        //设置TileMode的样式 CLAMP 拉伸 REPEAT 重复  MIRROR 镜像
        paint.shader = BitmapShader(square, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val r = size / 2f
        //画圆
        canvas.drawCircle(r, r, r, paint)
        return circle
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj is BlurTransformation) {
            this === obj
        } else false
    }

    override fun hashCode(): Int {
        return Util.hashCode(TAG.hashCode())
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(TAG.toByteArray(Key.CHARSET))
    }
}
