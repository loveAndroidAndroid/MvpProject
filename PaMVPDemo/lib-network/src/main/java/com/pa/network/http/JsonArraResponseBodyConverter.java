package com.pa.network.http;

import com.google.gson.Gson;
import com.pa.network.bean.ErrResponse;
import com.pa.network.bean.ResultException;
import com.pa.network.bean.ResultResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by wen on 2018/5/14.
 */

public class JsonArraResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Gson gson;
    private Type type;

    JsonArraResponseBodyConverter(Gson gson, Type type) {
        this.type = type;
        this.gson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            //ResultResponse 只解析status字段
            ResultResponse resultResponse = gson.fromJson(response, ResultResponse.class);
            if (Integer.parseInt(resultResponse.getStatus()) == 200) {
                //result==200表示成功返回，继续用本来的Model类解析
                return gson.fromJson(response, type);
            } else {
                //ErrResponse 将msg解析为异常消息文本
                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);
                throw new ResultException(resultResponse.getStatus(), errResponse.getMsg());
            }
        } finally {
        }
    }
}
