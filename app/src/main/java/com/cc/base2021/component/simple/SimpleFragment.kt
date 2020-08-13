package com.cc.base2021.component.simple

import com.cc.base.ext.logE
import com.cc.base2021.R
import com.cc.base2021.comm.CommFragment
import kotlinx.android.synthetic.main.fragment_simple.simpleTv

/**
 * Author:case
 * Date:2020/8/11
 * Time:20:29
 */
class SimpleFragment : CommFragment() {
  private var msg = "SimpleFragment"

  companion object {
    fun newInstance(msg: String): SimpleFragment {
      val fragment = SimpleFragment()
      fragment.msg = msg
      return fragment
    }
  }

  override val contentXmlId = R.layout.fragment_simple

  override fun lazyInitView() {
    "SimpleFragment:(${msg}懒加载)".logE()
    simpleTv.text = msg
  }

  override fun lazyInitDta() {
  }
}