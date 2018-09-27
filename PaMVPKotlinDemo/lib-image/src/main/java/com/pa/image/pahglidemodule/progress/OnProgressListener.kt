package com.pa.image.pahglidemodule.progress

/**
 * 定义回调接口
 */
interface OnProgressListener{
    fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long)
}
