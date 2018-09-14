package com.pa.network.http;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by wen on 2018/5/14.
 */
public class JsonArrayConverterFactory extends Converter.Factory {

    private Gson gson;

    public static JsonArrayConverterFactory create(Gson gson) {
        return new JsonArrayConverterFactory(gson);

    }
    private JsonArrayConverterFactory(Gson gson) {
        this.gson =gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new JsonArraResponseBodyConverter(gson,type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new JsonArraResponseBodyConverter(gson,type);
    }
}
