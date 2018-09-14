package com.pa.core.nethelper;


import com.pa.core.config.NetworkConfig;
import com.pa.network.http.RetrofitUtil;

import retrofit2.Retrofit;

/**
 * Created by zhangxiaowen on 2018/8/7.Context context
 */
public class RetrofitManger {

    private static volatile RetrofitManger instance = null;

    private RetrofitManger() {
    }

    /**
     * 静态内部类
     */
    public static RetrofitManger getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitManger();
                }
            }
        }
        return instance;
    }

    public void init(NetworkConfig mNetworkConfig){
        RetrofitUtil.getInstance().init(mNetworkConfig.baseURL,mNetworkConfig.readTime,mNetworkConfig.writeTime,mNetworkConfig.connectTime, null, null,mNetworkConfig.interceptors);
    }

    public Retrofit createRetrofit(){
        return RetrofitUtil.getInstance().getRetrofit();
    }
}
