package com.example.wen.pamvpdemo.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.wen.pamvpdemo.http.ApiUrls
import com.pa.core.config.GlobalConfigManager
import com.pa.core.config.ImageLoaderConfig
import com.pa.core.config.NetworkConfig
import com.pa.core.nethelper.AddCookiesInterceptor
import com.pa.core.nethelper.HttpLoggingInterceptor
import com.pa.core.nethelper.ReceivedCookiesInterceptor

/**
 *  Created by wen on 2018/5/14.
 */
class BaseApp : Application() {

    //同一模块下可见  internal arrayOf创建一个数组
    internal var interceptors =
            arrayOf(HttpLoggingInterceptor(HttpLoggingInterceptor.Level.BODY), ReceivedCookiesInterceptor(this), AddCookiesInterceptor(this))

    override fun onCreate() {
        super.onCreate()
        //全局上下文
        context = this

        //必须传入baseUrl readtime  writetime connecttime 默认为30
        //拦截器默认为HttpLoggingInterceptor 可通过setInterceptor 添加
        val networkConfig = NetworkConfig.Builder()
                .setBaseURL(ApiUrls.BaseUrl)
                .setReadTime(20)
                .setWriteTime(20)
                .setConnectTime(20)
                .setInterceptor(interceptors)
                .build()

        val imageLoaderConfig = ImageLoaderConfig.Builder()
                .setCacheUrl(CACHE_PHOTO)
                .setDiskCacheSizeBytes(1024 * 1024 * 250)
                .setMemorySize(1024 * 1024 * 100)
                .setContexts(this)
                .build()


        GlobalConfigManager.initNet(networkConfig)
        GlobalConfigManager.initImageLoader(imageLoaderConfig)
    }


    /*   let函数学习
     没有使用let函数的代码是这样的，看起来不够优雅
     mVideoPlayer?.setVideoView(activity.course_video_view)
     mVideoPlayer?.setControllerView(activity.course_video_controller_view)
     mVideoPlayer?.setCurtainView(activity.course_video_curtain_view)123

     使用let函数后的代码是这样的

     mVideoPlayer?.let {
          it.setVideoView(activity.course_video_view)
          it.setControllerView(activity.course_video_controller_view)
          it.setCurtainView(activity.course_video_curtain_view)
     }
     *缩减断语句的 if 显示比较，很好用
     fun orderDetail(contex: Context, order: RespPushData.TextBean.Order?, refreshMethod: () -> Unit) {
     //if (order == null || TextUtils.isEmpty(order.order_id)) {
     //     return
     //}
     order?.order_id?.let {
      //逻辑
        }
     }

        apply函数是这样的，调用某对象的apply函数，在函数范围内，可以任意调用该对象的任意方法，并返回该对象
        fun <T> T.apply(f: T.() -> Unit): T { f(); return this }1
        代码示例
        fun testApply() {
            // fun <T> T.apply(f: T.() -> Unit): T { f(); return this }
               ArrayList<String>().apply {
               add("testApply")
               add("testApply")
               add("testApply")
               println("this = " + this)
         }.let { println(it) }
        }
 *
 *     //companion object,需要说一下的是,Kotlin的class并不支持static变量
       //所以需要使用companion object来声明static变量,其实这个platformStatic变量也不是真正的static变量,而是一个伴生对象,
       //Kotlin语言中使用"companion object"修饰静态方法，可以使用类名.方法名的形式调用
 *     also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，返回函数体内最后一行的值，
 *     如果最后一行为空就返回一个Unit类型的默认值。而also函数返回的则是传入对象的本身
 *     内敛函数学习：https://www.jb51.net/article/136517.htm
 * */
    companion object {

        //全局上下文
        @SuppressLint("StaticFieldLeak")
        @Volatile internal var context: Context? = null

        //如果 ?: 左侧表达式非空，就返回其左侧表达式，否则返回右侧表达式
        //it指代当前对象  also把创建的BaseApp()对象赋值给it
        fun getInstance(): Context {

            return context ?: synchronized(this) {
                context ?: BaseApp().also { context = it }
            }
        }

        /**
         * 图片缓存路径
         */
        val CACHE_PHOTO = "Photo"
    }
}
