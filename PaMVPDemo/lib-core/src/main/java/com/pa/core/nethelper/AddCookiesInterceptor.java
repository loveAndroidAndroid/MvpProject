package com.pa.core.nethelper;

import android.content.Context;

import com.pa.core.utils.SharedPreUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wen on 2018/5/14.
 */
public class AddCookiesInterceptor implements Interceptor {

    private Context mContext;

    public AddCookiesInterceptor(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        Set<String> stringSet = SharedPreUtils.getStringSet(mContext, Constant.SESSIONID, new HashSet<String>());
        for (String cookie : stringSet) {
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }
}
