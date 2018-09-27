package com.pa.core.config

import android.content.Context

class ImageLoaderConfig private constructor(builder: Builder) {
    val cacheUrl: String?
    val diskCacheSizeBytes: Int
    val memorySize: Int
    val context: Context?

    init {
        this.cacheUrl = builder.cacheUrl
        this.diskCacheSizeBytes = builder.diskCacheSizeBytes
        this.memorySize = builder.memorySize
        this.context = builder.context
    }

    class Builder {
        var cacheUrl: String? = null
        var diskCacheSizeBytes = 30
        var memorySize = 30
        var context: Context? = null


        fun setCacheUrl(cacheUrl: String): Builder {
            this.cacheUrl = cacheUrl
            return this
        }

        fun setDiskCacheSizeBytes(diskCacheSizeBytes: Int): Builder {
            this.diskCacheSizeBytes = diskCacheSizeBytes
            return this
        }

        fun setMemorySize(memorySize: Int): Builder {
            this.memorySize = memorySize
            return this
        }

        fun setContexts(context: Context): Builder {
            this.context = context
            return this
        }

        fun build(): ImageLoaderConfig {
            return ImageLoaderConfig(this)
        }
    }
}
