package com.cc.base2021.component.main

import android.content.Context
import android.content.Intent
import androidx.annotation.IntRange
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.FragmentUtils
import com.cc.base.ext.toast
import com.cc.base.ui.BaseFragment
import com.cc.base2021.R
import com.cc.base2021.R.layout
import com.cc.base2021.comm.CommActivity
import com.cc.base2021.component.main.fragment.HomeFragment
import com.cc.base2021.component.simple.SimpleFragment
import com.cc.base2021.component.simple.SimpleTimeFragment
import kotlinx.android.synthetic.main.activity_main.mainContainer
import kotlinx.android.synthetic.main.activity_main.mainNavigation

class MainActivity : CommActivity() {
  //<editor-fold defaultstate="collapsed" desc="外部跳转">
  companion object {
    fun startActivity(context: Context) {
      val intent = Intent(context, MainActivity::class.java)
      context.startActivity(intent)
    }
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="XML">
  override fun layoutResId() = layout.activity_main
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="变量">
  //页面
  private lateinit var homeFragment: BaseFragment
  private lateinit var dynFragment: BaseFragment
  private lateinit var mineFragment: BaseFragment

  //当前页面
  private var currentFragment: BaseFragment? = null

  //子列表合集，方便外部调用选中那个
  private var fragmentList = mutableListOf<BaseFragment>()

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="初始化View">
  override fun initView() {
    //初始化
    homeFragment = HomeFragment.newInstance()
    dynFragment = SimpleFragment.newInstance("B")
    mineFragment = SimpleTimeFragment.newInstance("请求北京时间")
    //添加
    fragmentList = mutableListOf(homeFragment, dynFragment, mineFragment)
    //设置选中
    selectFragment(0)
    setSelectIndex(0)
    //切换
    mainNavigation.setOnNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.menu_main_home -> selectFragment(0)
        R.id.menu_main_dyn -> selectFragment(1)
        R.id.menu_main_mine -> selectFragment(2)
      }
      true //返回true让其默认选中点击的选项
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="设置选中的fragment">
  //设置选中的fragment
  private fun selectFragment(@IntRange(from = 0, to = 2) index: Int) {
    //需要显示的fragment
    val f = fragmentList[index]
    //和当前选中的一样，则不再处理
    if (currentFragment == f) {
      f.scroll2Top()
      return
    }
    //先关闭之前显示的
    currentFragment?.let { FragmentUtils.hide(it) }
    //设置现在需要显示的
    currentFragment = f
    if (!f.isAdded) { //没有添加，则添加并显示
      FragmentUtils.add(supportFragmentManager, f, mainContainer.id, "${f::class.java.simpleName}_${f.hashCode()}", false)
    } else { //添加了就直接显示
      FragmentUtils.show(f)
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="外部设置选中">
  //外部调用选中哪一个tab
  fun setSelectIndex(@IntRange(from = 0, to = 2) index: Int) {
    val selectId = when (index) {
      1 -> R.id.menu_main_dyn
      2 -> R.id.menu_main_mine
      else -> R.id.menu_main_home
    }
    mainNavigation?.post {
      if (mainNavigation.selectedItemId != selectId) mainNavigation.selectedItemId = selectId
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="初始化数据">
  override fun initData() {
    //关闭其他所有页面
    ActivityUtils.finishOtherActivities(javaClass)
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="再按一次退出">
  private var touchTime = 0L
  private val waitTime = 2000L
  override fun onBackPressed() {
    val currentTime = System.currentTimeMillis()
    if (currentTime - touchTime >= waitTime) {
      //让Toast的显示时间和等待时间相同
      toast(R.string.double_exit)
      touchTime = currentTime
    } else {
      //AppUtils.exitApp()
      super.onBackPressed()
    }
  }
  //</editor-fold>
}