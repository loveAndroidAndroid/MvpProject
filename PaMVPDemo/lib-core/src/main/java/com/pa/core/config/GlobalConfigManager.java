package com.pa.core.config;

import com.pa.core.imagehelper.GlideManger;
import com.pa.core.nethelper.RetrofitManger;

public class GlobalConfigManager {

    public static final void initNet(NetworkConfig networkConfig){
        RetrofitManger.getInstance().init(networkConfig);
    }

    public static final void initImageLoader(ImageLoaderConfig imageLoaderConfig){
        GlideManger.getInstance().init(imageLoaderConfig);
    }
}
