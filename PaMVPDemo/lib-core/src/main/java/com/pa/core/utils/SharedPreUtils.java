package com.pa.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * sp工具类
 */
public class SharedPreUtils {

    public static volatile SharedPreferences sp = null;

    public static void getSharedPreference(Context context) {
        if (sp == null) {
            synchronized (SharedPreUtils.class) {
                if (sp == null) {
                    sp = context.getSharedPreferences("rmyh", Context.MODE_PRIVATE);
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
    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        // 获取sp的对象
        getSharedPreference(context);
        boolean aBoolean = sp.getBoolean(key, defValue);
        return aBoolean;
    }

    public static void putBoolean(Context context, String key, boolean defValue) {
        getSharedPreference(context);
        sp.edit().putBoolean(key, defValue).commit();
    }

    /**
     * int操作
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key,
                             int defValue) {
        // 获取sp的对象
        getSharedPreference(context);
        int result = sp.getInt(key, defValue);
        return result;
    }

    public static void putInt(Context context, String key, int defValue) {
        getSharedPreference(context);
        sp.edit().putInt(key, defValue).commit();
    }

    /**
     * 字符串操作
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key,
                                   String defValue) {
        // 获取sp的对象
        getSharedPreference(context);
        String result = sp.getString(key, defValue);
        return result;
    }

    public static void putString(Context context, String key, String defValue) {
        getSharedPreference(context);
        sp.edit().putString(key, defValue).commit();
    }

    /**
     * 集合操作
     *
     * @param context
     * @param key
     * @return
     */
    public static Set<String> getStringSet(Context context, String key,
                                           Set<String> set) {
        // 获取sp的对象
        getSharedPreference(context);
        Set<String> result = sp.getStringSet(key, set);
        return result;
    }

    public static void putStringSet(Context context, String key, Set<String> set) {
        getSharedPreference(context);
        sp.edit().putStringSet(key, set).commit();
    }
}
