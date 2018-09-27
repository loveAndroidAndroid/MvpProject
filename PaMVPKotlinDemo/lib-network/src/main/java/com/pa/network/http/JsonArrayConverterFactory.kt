package com.pa.network.http

import com.google.gson.Gson
import java.lang.reflect.Type

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Created by wen on 2018/5/14.
 */
class JsonArrayConverterFactory private constructor(private val gson: Gson?) : Converter.Factory() {

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?,
                                       retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return JsonArraResponseBodyConverter<Converter<ResponseBody, *>>(gson, type)
    }

    override fun requestBodyConverter(type: Type?,
                                      parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        return JsonArraResponseBodyConverter(gson, type)
    }

    companion object {

        fun create(gson: Gson?): JsonArrayConverterFactory {
            return JsonArrayConverterFactory(gson)
        }
    }
}
