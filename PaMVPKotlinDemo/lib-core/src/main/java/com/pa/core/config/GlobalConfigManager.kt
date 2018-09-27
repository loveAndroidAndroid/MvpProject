package com.pa.core.config

import com.pa.core.imagehelper.GlideManger
import com.pa.core.nethelper.RetrofitManger

object GlobalConfigManager {

    fun initNet(networkConfig: NetworkConfig) {
        RetrofitManger.getInstance()?.init(networkConfig)
    }

    fun initImageLoader(imageLoaderConfig: ImageLoaderConfig) {
        GlideManger.getInstance()?.init(imageLoaderConfig)
    }
}
