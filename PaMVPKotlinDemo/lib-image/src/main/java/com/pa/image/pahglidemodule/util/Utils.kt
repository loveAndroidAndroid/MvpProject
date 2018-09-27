package com.pa.image.pahglidemodule.util

import android.content.Context
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * 常用工具类
 */
object Utils {

    private var windowManager: WindowManager? = null

    private fun getWindowManager(context: Context): WindowManager {
        if (windowManager == null) {
            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        return windowManager as WindowManager
    }

    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    fun getFontDensity(context: Context): Float {
        return context.resources.displayMetrics.scaledDensity
    }

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        getWindowManager(context).defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    fun dp2px(context: Context, dp: Float): Int {
        return (getDensity(context) * dp + 0.5f).toInt()
    }

    fun px2dp(context: Context, px: Float): Int {
        return (px / getDensity(context) + 0.5f).toInt()
    }

    fun sp2px(context: Context, sp: Float): Int {
        return (getFontDensity(context) * sp + 0.5f).toInt()
    }

    fun px2sp(context: Context, px: Float): Int {
        return (px / getFontDensity(context) + 0.5f).toInt()
    }

    fun getWindowWidth(context: Context): Int {
        return getDisplayMetrics(context).widthPixels
    }

    fun getWindowHeight(context: Context): Int {
        return getDisplayMetrics(context).heightPixels
    }

    fun getPathFormat(path: String): String {
        if (!TextUtils.isEmpty(path)) {
            val lastPeriodIndex = path.lastIndexOf('.')
            if (lastPeriodIndex > 0 && lastPeriodIndex + 1 < path.length) {
                val format = path.substring(lastPeriodIndex + 1)
                if (!TextUtils.isEmpty(format)) {
                    return format.toLowerCase()
                }
            }
        }
        return ""
    }

    fun isGif(url: String): Boolean {
        return "gif" == getPathFormat(url)
    }

    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun getSize(collection: Collection<*>?): Int {
        return collection?.size ?: 0
    }
}
