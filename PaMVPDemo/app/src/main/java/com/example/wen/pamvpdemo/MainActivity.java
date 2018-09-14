package com.example.wen.pamvpdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.pa.core.imagehelper.GlideManger;
import com.pa.image.pahglidemodule.progress.CircleProgressView;
import com.pa.image.pahglidemodule.progress.OnProgressListener;
import com.pa.image.pahglidemodule.util.GlideCacheUtil;


public class MainActivity extends Activity {

    ImageView image11;
    ImageView image12;
    ImageView image13;
    ImageView image14;

    ImageView image21;
    ImageView image22;
    ImageView image23;
    ImageView image24;

    ImageView image31;
    CircleProgressView progressView1;
    ImageView image41;
    CircleProgressView progressView2;

    ImageView image51;

    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497688355699&di=ea69a930b82ce88561c635089995e124&imgtype=0&src=http%3A%2F%2Fcms-bucket.nosdn.127.net%2Ff84e566bcf654b3698363409fbd676ef20161119091503.jpg";

    String gif1 = "http://img.zcool.cn/community/01e97857c929630000012e7e3c2acf.gif";
    String gif2 = "http://5b0988e595225.cdn.sohucs.com/images/20171202/a1cc52d5522f48a8a2d6e7426b13f82b.gif";
    String gif3 = "http://img.zcool.cn/community/01d6dd554b93f0000001bf72b4f6ec.jpg";

    String girl = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/girl.jpg";
    String cat = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/cat.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        image11 = findViewById(R.id.image11);
        image12 = findViewById(R.id.image12);
        image13 = findViewById(R.id.image13);
        image14 = findViewById(R.id.image14);

        image21 = findViewById(R.id.image21);
        image22 = findViewById(R.id.image22);
        image23 = findViewById(R.id.image23);
        image24 = findViewById(R.id.image24);
        image31 = findViewById(R.id.image31);

        progressView1 = findViewById(R.id.progressView1);
        image41 = findViewById(R.id.image32);

        progressView2 = findViewById(R.id.progressView2);
        image51 = findViewById(R.id.image41);

        line1();
        line2();
        line3();
        line4();
        line5();
    }

    private void line1() {

        GlideManger.getInstance().setImage(url, image11, R.mipmap.image_loading);
        GlideManger.getInstance().setImageCircle(url, image12, R.mipmap.image_loading);
        GlideManger.getInstance().setImage(url, image13, R.mipmap.image_loading);
        GlideManger.getInstance().setImageRound(url, image14, R.mipmap.image_loading, true);
    }

    private void line2() {
        GlideManger.getInstance().setImageRound(gif2, image21, R.mipmap.image_loading, true);
        GlideManger.getInstance().setImage(gif1, image22, R.mipmap.image_loading);
        GlideManger.getInstance().setImageCircle(gif3, image23, R.mipmap.image_loading);
    }

    private void line3() {
        GlideManger.getInstance().setImage(cat, image31, R.color.transparent10, new OnProgressListener() {

            @Override
            public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                if (isComplete) {
                    progressView1.setVisibility(View.GONE);
                } else {
                    progressView1.setVisibility(View.VISIBLE);
                    progressView1.setProgress(percentage);
                }
            }
        });
    }

    /**
     * 缓存
     */
    private void line4() {
        GlideManger.getInstance().setImage(girl, image41, R.color.transparent10, new OnProgressListener() {

            @Override
            public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                if (isComplete) {
                    progressView2.setVisibility(View.GONE);
                } else {
                    progressView2.setVisibility(View.VISIBLE);
                    progressView2.setProgress(percentage);
                }
            }
        });
    }

    /**
     * 模糊图
     */
    private void line5() {
        GlideManger.getInstance().setImage(url, image51, R.color.transparent10);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //清除缓存的方法
        GlideCacheUtil.getInstance().clearImageAllCache(this);
    }
}
