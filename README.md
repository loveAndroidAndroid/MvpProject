# MvpProject

Mvp封装

本Demo有retorfit+OkHttp+Rxjava 2.x +Glide++Gson+MVP组成,采用组件方式构成，可直接使用。

架构组成：
APPMouble：主项目
        1.功能使用代码
lib-core:公共模块，隔离层代码,对基础模块封装了一层，对外暴露方法.
        1.MVP架构（自动管理请求生命周期，各种公共提示等）
        2.进度圈base封装 
        3.图片代理类
        4.网络代理类
        5.全局初始化代理config
        6.......
lib-image:图片库封装，可直接使用
        1.图片加载进度监听
        2.圆效果
        3.圆角效果
        4.模糊处理效果
        5.缓存处理封装
        6.......
lib-network:网络封装库：可直接使用
        1.自定义Converter处理异常，分步骤解析，防止因服务器返回数据类型引发的解析异常错误并加快解析速度。
        2.自定义DisposableSubscriber实现异常处理提示
        3.RetrofitUtil封装，支持Https验证，域名验证设置
        4.......

涉及设计模式：
单例模式
构建者模式
代理模式
适配器模式
观察者模式
