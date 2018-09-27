package com.example.wen.pamvpdemo

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.pa.core.imagehelper.GlideManger
import com.pa.image.pahglidemodule.progress.OnProgressListener
import com.pa.image.pahglidemodule.util.GlideCacheUtil
import kotlinx.android.synthetic.main.activity_main1.*


class MainActivity : Activity() {

    companion object {

        internal val url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497688355699&di=ea69a930b82ce88561c635089995e124&imgtype=0&src=http%3A%2F%2Fcms-bucket.nosdn.127.net%2Ff84e566bcf654b3698363409fbd676ef20161119091503.jpg"

        internal val gif1 = "http://img.zcool.cn/community/01e97857c929630000012e7e3c2acf.gif"
        internal val gif2 = "http://5b0988e595225.cdn.sohucs.com/images/20171202/a1cc52d5522f48a8a2d6e7426b13f82b.gif"
        internal val gif3 = "http://img.zcool.cn/community/01d6dd554b93f0000001bf72b4f6ec.jpg"

        internal val girl = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/girl.jpg"
        internal val cat = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/cat.jpg"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)


        line1()
        line2()
        line3()
        line4()
        line5()
    }

    private fun line1() {
        //?.和！！的区别  ?. 与 !!. 都是Kotlin提供的检测空指针的方法。
        //"?"加在变量名后，系统在任何情况不会报它的空指针异常。  不为空才进行以后的代码逻辑
        //"!!"加在变量名后，如果对象为null，那么系统一定会报异常！ 抛出异常
        GlideManger.getInstance()?.setImage(url, image11, R.mipmap.image_loading)
        GlideManger.getInstance()?.setImageCircle(url, image12, R.mipmap.image_loading)
        GlideManger.getInstance()?.setImage(url, image13, R.mipmap.image_loading)
        GlideManger.getInstance()?.setImageRound(url, image14, R.mipmap.image_loading, true)
    }

    private fun line2() {
        GlideManger.getInstance()?.setImageRound(gif2, image21, R.mipmap.image_loading, true)
        GlideManger.getInstance()?.setImage(gif1, image22, R.mipmap.image_loading)
        GlideManger.getInstance()?.setImageCircle(gif3, image23, R.mipmap.image_loading)
    }

    /**
     * 匿名内部类的使用
     * 也可实现多个接口和类  object : AA, BB() 对比类的实现方式
     */
    private fun line3() {
        GlideManger.getInstance()?.setImage(cat,
                image31,
                R.color.transparent10,
                object : OnProgressListener {
                    override fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) {
                        if (isComplete) {
                            progressView1.visibility = View.GONE
                        } else {
                            progressView1.visibility = View.VISIBLE
                            progressView1.progress = percentage
                        }
                    }
                }
        )
    }


    /**
     * 缓存
     */
    private fun line4() {
        GlideManger.getInstance()?.setImage(
                girl,
                image41,
                R.color.transparent10,
                object : OnProgressListener {
                    override fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) {
                        if (isComplete) {
                            progressView2.visibility = View.GONE
                        } else {
                            progressView2.visibility = View.VISIBLE
                            progressView2.progress = percentage
                        }
                    }
                }
        )
    }

    /**
     * 模糊图
     */
    private fun line5() {
        GlideManger.getInstance()?.setImage(url, image32, R.color.transparent10)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        //清除缓存的方法
        GlideCacheUtil.instance?.clearImageAllCache(this)
    }
}
