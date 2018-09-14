package com.pa.core.config;

import okhttp3.Interceptor;

public class NetworkConfig {

    public final String baseURL;
    public final long readTime;
    public final long writeTime;
    public final long connectTime;
    public final Interceptor[] interceptors;

    private NetworkConfig(Builder builder){
        this.baseURL = builder.baseURL;
        this.readTime = builder.readTime;
        this.writeTime = builder.writeTime;
        this.connectTime = builder.connectTime;
        this.interceptors = builder.interceptors;
    }

    public static class Builder{
        String baseURL;
        long readTime = 30;
        long writeTime = 30;
        long connectTime = 30;
        Interceptor[] interceptors = null;


        public Builder setBaseURL(String baseURL){
            this.baseURL = baseURL;
            return this;
        }

        public Builder setReadTime(long readTime){
            this.readTime = readTime;
            return this;
        }

        public Builder setWriteTime(long writeTime){
            this.writeTime = writeTime;
            return this;
        }

        public Builder setConnectTime(long connectTime){
            this.connectTime = connectTime;
            return this;
        }

        public Builder setInterceptor(Interceptor... interceptors){
            this.interceptors = interceptors;
            return this;
        }

        public NetworkConfig build(){
            return new NetworkConfig(this);
        }
    }
}
