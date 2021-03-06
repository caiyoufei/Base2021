object Versions {
  internal const val sdkMin = 23
  internal const val sdkTarget = 30
  internal const val kotlin = "1.4.21"
  internal const val okHttp = "4.9.0"
  internal const val rxHttp = "2.5.1"
  internal const val coil = "1.1.0"
}

object Deps {
  //根目录gradle https://maven.aliyun.com/mvn/search
  const val plugin_android_gradle = "com.android.tools.build:gradle:3.5.4"
  const val plugin_kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  const val plugin_r8_gradle = "com.android.tools:r8:2.1.67" //更新R8版本，解决正式版无法打包的问题 https://github.com/square/okhttp/issues/4604
  const val plugin_pandora = "com.github.whataa:pandora-plugin:1.0.1"

  //使用kotlin
  const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

  //系统相关 https://maven.aliyun.com/mvn/search
  const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha02"
  const val core_ktx = "androidx.core:core-ktx:1.5.0-alpha05"
  const val activity_ktx = "androidx.activity:activity-ktx:1.2.0-beta02"
  const val fragment = "androidx.fragment:fragment:1.3.0-rc01"
  const val material = "com.google.android.material:material:1.3.0-beta01"
  const val constraint = "androidx.constraintlayout:constraintlayout:2.1.0-alpha1"

  //启动初始化 https://developer.android.google.cn/topic/libraries/app-startup
  const val startup = "androidx.startup:startup-runtime:1.0.0"

  //分包 https://developer.android.google.cn/studio/build/multidex?hl=zh_cn#mdex-gradle
  const val multidex = "androidx.multidex:multidex:2.0.1"

  //内存泄漏检测 https://square.github.io/leakcanary/getting_started/
  const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.5"

  //潘多拉调试工具 https://github.com/whataa/pandora/blob/master/README_CN.md
  const val pandora = "com.github.whataa:pandora:androidx_v2.1.0"
  const val pandora_no = "com.github.whataa:pandora-no-op:v2.0.3"

  //工具类 https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md
  const val utilcodex = "com.blankj:utilcodex:1.30.5"

  //防止混淆 https://github.com/Blankj/FreeProGuard
  const val proguardx = "com.blankj:free-proguard:1.0.2"

  //log打印 https://github.com/JakeWharton/timber
  const val timber = "com.jakewharton.timber:timber:4.7.1"

  //UI适配 https://github.com/JessYanCoding/AndroidAutoSize
  const val autosize = "me.jessyan:autosize:1.2.1"

  //状态栏适配 https://github.com/gyf-dev/ImmersionBar
  const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0"
  const val immersionbar_ktx = "com.gyf.immersionbar:immersionbar-ktx:3.0.0"

  //数据存储 https://github.com/Tencent/MMKV
  const val mmkv = "com.tencent:mmkv-static:1.2.6"

  //数据解析 https://github.com/google/gson
  const val gson = "com.google.code.gson:gson:2.8.6"

  //网络请求 https://github.com/square/okhttp
  const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

  //日志json打印 https://github.com/Ayvytr/OKHttpLogInterceptor
  const val okhttp_log = "com.ayvytr:okhttploginterceptor:3.0.4"

  //协程 https://github.com/Kotlin/kotlinx.coroutines
  const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2" //在Kotlin中使用协程的主要接口
  const val coroutine_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2" //支持协同程序中的Android主线程

  //RxHttp https://github.com/liujingxing/okhttp-RxHttp
  const val rxhttp = "com.ljx.rxhttp:rxhttp:${Versions.rxHttp}"
  const val rxhttp_kapt = "com.ljx.rxhttp:rxhttp-compiler:${Versions.rxHttp}" //生成RxHttp类
  const val rxlife = "com.ljx.rxlife:rxlife-coroutine:2.0.1" //管理协程生命周期，页面销毁，关闭请求

  //RxAndroid https://github.com/ReactiveX/RxAndroid
  const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"

  //动态权限请求 https://github.com/getActivity/XXPermissions
  const val permissions = "com.github.getActivity:XXPermissions:11.5"

  //EventBus https://github.com/JeremyLiao/LiveEventBus
  const val eventBus = "com.jeremyliao:live-event-bus-x:1.7.3"

  //WebView https://github.com/Justson/AgentWeb
  const val agentweb = "com.just.agentweb:agentweb:4.1.4"

  //侧滑 https://github.com/luckybilly/SmartSwipe
  const val swipe = "com.billy.android:smart-swipe:1.1.2"
  const val swipex = "com.billy.android:smart-swipe-x:1.1.0"

  //下拉刷新 https://github.com/scwang90/SmartRefreshLayout
  const val smart_refresh = "com.scwang.smart:refresh-layout-kernel:2.0.1"
  const val smart_header = "com.scwang.smart:refresh-header-classics:2.0.1"
  const val smart_footer = "com.scwang.smart:refresh-footer-classics:2.0.1"

  //图片加载 https://github.com/coil-kt/coil
  const val coil = "io.coil-kt:coil:${Versions.coil}"
  const val coil_gif = "io.coil-kt:coil-gif:${Versions.coil}"
  const val coil_video_file = "io.coil-kt:coil-video:${Versions.coil}"

  //多类型适配器 https://github.com/drakeet/MultiType
  const val multitype = "com.drakeet.multitype:multitype:4.2.0"

  //SVGA动画 https://github.com/svga/SVGAPlayer-Android/blob/master/readme.zh.md
  const val svga = "com.github.yyued:SVGAPlayer-Android:2.5.14"

  //图片选择器 https://github.com/LuckSiege/PictureSelector
  const val pic_select = "com.github.LuckSiege.PictureSelector:picture_library:v2.6.0"

  //打包资源压缩 https://github.com/smallSohoSolo/McImage/blob/master/README-CN.md
  const val mc_image = "com.smallsoho.mobcase:McImage:1.5.1"

  //阿里播放器官网 https://help.aliyun.com/document_detail/124711.html?spm=a2c4g.11186623.6.1085.11b511f9ytuUNH
  //阿里播放器Github https://github.com/alibaba/CicadaPlayer/tree/release/0.1/platform/Android
  const val ali_player = "com.aliyun.sdk.android:AliyunPlayer:5.2.2-full"
  const val ali_conan = "com.alivc.conan:AlivcConan:1.0.5"
}
