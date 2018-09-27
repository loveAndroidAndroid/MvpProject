package com.pa.image.pahglidemodule.util

import android.content.Context
import android.os.Looper
import android.text.TextUtils

import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.pa.image.pahglidemodule.progress.GlideApp

import java.io.File
import java.math.BigDecimal

/*
 *Glide缓存工具类
 */
class GlideCacheUtil private constructor() {

    /**
     * 清除图片磁盘缓存，只能在子线程中执行
     */
    fun clearImageDiskCache(context: Context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Thread(Runnable { GlideApp.get(context).clearDiskCache() }).start()
            } else {
                GlideApp.get(context).clearDiskCache()
            }
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
            if (Looper.myLooper() == Looper.getMainLooper()) {
                GlideApp.get(context).clearMemory()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 清除图片所有缓存
     */
    fun clearImageAllCache(context: Context) {
        clearImageDiskCache(context)
        clearImageMemoryCache(context)
        val ImageExternalCatchDir = context.externalCacheDir!!.toString() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR
        deleteFolderFile(ImageExternalCatchDir, true)
    }

    /**
     * 获取Glide造成的缓存大小
     */
    fun getCacheSize(context: Context): String {
        try {
            return getFormatSize(getFolderSize(File(context.cacheDir.toString() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)).toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     */
    @Throws(Exception::class)
    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (aFileList in fileList) {
                if (aFileList.isDirectory) {
                    size = size + getFolderSize(aFileList)
                } else {
                    size = size + aFileList.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     */
    private fun deleteFolderFile(filePath: String, deleteThisPath: Boolean) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                val file = File(filePath)
                if (file.isDirectory) {
                    val files = file.listFiles()
                    for (file1 in files) {
                        deleteFolderFile(file1.absolutePath, true)
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory) {
                        file.delete()
                    } else {
                        if (file.listFiles().size == 0) {
                            file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    companion object {

        @Volatile private var inst: GlideCacheUtil? = null

        val instance: GlideCacheUtil?
            get() {
                if (inst == null) {
                    synchronized(GlideCacheUtil::class.java) {
                        if (inst == null) {
                            inst = GlideCacheUtil()
                        }
                    }
                }
                return inst
            }

        /**
         * 格式化单位
         */
        private fun getFormatSize(size: Double): String {

            val kiloByte = size / 1024
            if (kiloByte < 1) {
                return size.toString() + "Byte"
            }

            val megaByte = kiloByte / 1024
            if (megaByte < 1) {
                val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
            }

            val gigaByte = megaByte / 1024
            if (gigaByte < 1) {
                val result2 = BigDecimal(java.lang.Double.toString(megaByte))
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
            }

            val teraBytes = gigaByte / 1024
            if (teraBytes < 1) {
                val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)

            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
        }
    }
}