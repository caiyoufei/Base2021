package com.cc.video.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cc.ext.gone
import com.cc.ext.visible
import com.cc.utils.MySizeUtils
import com.cc.video.R
import com.cc.video.enu.PlayState
import com.cc.video.inter.call.VideoLoadingCallListener
import kotlinx.android.synthetic.main.layout_video_loading.view.loading_kbs
import kotlinx.android.synthetic.main.layout_video_loading.view.loading_view

/**
 * 播放器回调loading状态显示
 * Author:Khaos
 * Date:2020-9-19
 * Time:10:29
 */
@SuppressLint("SetTextI18n")
class VideoLoadingView @JvmOverloads constructor(
    private val con: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(con, attrs, defStyleAttr, defStyleRes), VideoLoadingCallListener {

  //<editor-fold defaultstate="collapsed" desc="初始化XML">
  init {
    LayoutInflater.from(con).inflate(R.layout.layout_video_loading, this, true)
    loading_kbs.text = "0%"
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="播放器状态回调">
  override fun callPlayState(state: PlayState) {
    when (state) {
      PlayState.PREPARING -> {
        loading_kbs.gone()
        loading_view.visible()
      }
      PlayState.PREPARED -> {
        loading_view.gone()
        loading_kbs.text = "0%"
        loading_kbs.visible()
      }
      PlayState.BUFFING -> loading_view.visible()
      PlayState.BUFFED -> {
        loading_view.gone()
        loading_kbs.text = "0%"
      }
      else -> {

      }
    }
  }

  override fun callBufferPercent(percent: Int, kbps: Float) {
    loading_kbs.text = String.format("%d%%", percent)
    if (kbps > 0) loading_kbs.append(String.format(",%s /s", MySizeUtils.getPrintSize((kbps * 1024).toLong())))
  }
  //</editor-fold>
}