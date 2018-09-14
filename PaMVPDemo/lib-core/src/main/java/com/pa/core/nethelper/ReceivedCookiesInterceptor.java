package com.pa.core.nethelper;

import android.content.Context;
import android.util.Log;


import com.pa.core.utils.SharedPreUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wen on 2018/5/14.
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    private Context mContext;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.mContext = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.nanoTime();
        Request.Builder builder = request.newBuilder();
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SharedPreUtils.putStringSet(mContext, Constant.SESSIONID, cookies);
        }
        return originalResponse;
    }
}