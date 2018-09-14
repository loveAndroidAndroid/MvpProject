package com.pa.core.config;

import android.content.Context;

public class ImageLoaderConfig {
    public final String cacheUrl;
    public final int diskCacheSizeBytes;
    public final int memorySize;
    public final Context context;

    private ImageLoaderConfig(Builder builder){
        this.cacheUrl = builder.cacheUrl;
        this.diskCacheSizeBytes = builder.diskCacheSizeBytes;
        this.memorySize = builder.memorySize;
        this.context = builder.context;
    }

    public static class Builder{
        String cacheUrl;
        int diskCacheSizeBytes = 30;
        int memorySize = 30;
        Context context;


        public Builder setCacheUrl(String cacheUrl){
            this.cacheUrl = cacheUrl;
            return this;
        }

        public Builder setDiskCacheSizeBytes(int diskCacheSizeBytes){
            this.diskCacheSizeBytes = diskCacheSizeBytes;
            return this;
        }

        public Builder setMemorySize(int memorySize){
            this.memorySize = memorySize;
            return this;
        }

        public Builder setContexts(Context context){
            this.context = context;
            return this;
        }

        public ImageLoaderConfig build(){
            return new ImageLoaderConfig(this);
        }
    }
}
