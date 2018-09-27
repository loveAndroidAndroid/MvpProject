package com.pa.image.pahglidemodule.transformation

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import com.pa.image.pahglidemodule.util.Utils
import java.security.MessageDigest

/**
 * 画圆角
 */
class RadiusTransformation(context: Context, radius: Int) : BitmapTransformation() {
    private val TAG = javaClass.getName()

    private val radius: Int

    init {
        this.radius = Utils.dp2px(context, radius.toFloat())
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        //得到宽和高
        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setHasAlpha(true)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        //画圆角
        canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), radius.toFloat(), radius.toFloat(), paint)
        return bitmap
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is RadiusTransformation) {
            val other = obj as RadiusTransformation?
            return radius == other!!.radius
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(TAG.hashCode(), Util.hashCode(radius))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((TAG + radius).toByteArray(Key.CHARSET))
    }

}
