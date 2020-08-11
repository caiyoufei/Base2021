package com.cc.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.cc.base.BuildConfig
import com.cc.base.ext.logE
import com.cc.base.ext.logI
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * 由于有依赖关系，所以Manifest中编写最后初始化的即可
 * Author:case
 * Date:2020/8/11
 * Time:17:11
 */
class TimberInit : Initializer<Int> {
  override fun create(context: Context): Int {
    if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    "CASE-初始化完成".logI()
    return 0
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}