package com.cc.base2021.config

import com.blankj.utilcode.util.PathUtils
import java.io.File

/**
 * Description: 动态配置
 * @see AppConfig.defaultAppName 代码设置APP名称
 * @author: Khaos
 * @date: 2020/3/5 9:41
 */
class AppConfig {
  companion object {
    //是否需要自动登录
    val NEE_AUTO_LOGIN: Boolean = System.currentTimeMillis() > 0L

    //视频封面缓存地址
    val VIDEO_OVER_CACHE_DIR = PathUtils.getExternalPicturesPath() + File.separator + "VideoCover"
  }
}
