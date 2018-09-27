package com.pa.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.pa.core.utils.SharedPreUtils.sp

/**
 * sp工具类
 */
object SharedPreUtils {

    @Volatile
    var sp: SharedPreferences? = null

    fun getSharedPreference(context: Context) {
        if (sp == null) {
            synchronized(SharedPreUtils::class.java) {
                if (sp == null) {
                    sp = context.getSharedPreferences("aaa", Context.MODE_PRIVATE)
                }
            }
        }
    }

    /**
     * boolean操作
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    fun getBoolean(context: Context, key: String,
                   defValue: Boolean): Boolean? {
        // 获取sp的对象
        getSharedPreference(context)
        return sp?.getBoolean(key, defValue)
    }

    fun putBoolean(context: Context, key: String, defValue: Boolean) {
        getSharedPreference(context)
        sp?.edit()?.putBoolean(key, defValue)?.commit()
    }

    /**
     * int操作
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    fun getInt(context: Context, key: String,
               defValue: Int): Int? {
        // 获取sp的对象
        getSharedPreference(context)
        return sp?.getInt(key, defValue)
    }

    fun putInt(context: Context, key: String, defValue: Int) {
        getSharedPreference(context)
        sp?.edit()?.putInt(key, defValue)?.commit()
    }

    /**
     * 字符串操作
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    fun getString(context: Context, key: String,
                  defValue: String): String {
        // 获取sp的对象
        getSharedPreference(context)
        return sp!!.getString(key, defValue)
    }

    fun putString(context: Context, key: String, defValue: String) {
        getSharedPreference(context)
        sp?.edit()?.putString(key, defValue)?.commit()
    }

    /**
     * 集合操作
     *
     * @param context
     * @param key
     * @return
     */
    fun getStringSet(context: Context, key: String,
                     set: Set<String>): Set<String>? {
        // 获取sp的对象
        getSharedPreference(context)
        return sp?.getStringSet(key, set)
    }

    fun putStringSet(context: Context, key: String, set: Set<String>) {
        getSharedPreference(context)
        sp?.edit()?.putStringSet(key, set)?.commit()
    }
}
