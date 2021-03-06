package com.cc.video.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.cc.ext.gone
import com.cc.ext.visible
import com.cc.video.R
import com.cc.video.enu.PlayState
import com.cc.video.enu.PlayUiState
import com.cc.video.inter.*
import com.cc.video.inter.call.VideoGestureCallListener
import com.cc.video.inter.operate.VideoGestureListener
import com.cc.video.utils.VideoTimeUtils
import kotlinx.android.synthetic.main.layout_video_gesture.view.*
import kotlin.math.*

/**
 * 手势操作控制器，实现：
 * 1.左右滑动控制播放进度
 * 2.左边上下滑动控制亮度
 * 3.右边上下滑动控制音量
 * Author:Khaos
 * Date:2020-9-19
 * Time:18:22
 */
@SuppressLint("SetTextI18n")
class VideoGestureView @JvmOverloads constructor(
    con: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(con, attrs, defStyleAttr, defStyleRes), VideoGestureCallListener, OverGestureListener {

  //<editor-fold defaultstate="collapsed" desc="变量">
  //操作监听
  private var gestureListener: VideoGestureListener? = null

  //是否可以滑动屏幕操作
  private var canGestureVideo: Boolean = false

  //是否是直播
  private var isLive: Boolean = false
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="外部设置">
  //设置是否是直播(直播没有时长，播放进度等)
  fun setLiveVideo(live: Boolean) {
    isLive = live
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="初始化XML">
  init {
    LayoutInflater.from(con).inflate(R.layout.layout_video_gesture, this, true)
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="手势按下显示操作">
  private fun showBright() {
    gestureListener?.let { gl ->
      val cur = gl.getBrightCurrent()
      val min = gl.getBrightMin()
      downBright = cur
      gesture_bright_view.visible()
      gesture_bright_iv.setImageResource(if (cur == min) R.drawable.svg_media_bright_sub else R.drawable.svg_media_bright_add)
      gesture_bright_tv.text = String.format("%d%%", (cur * 100).toInt())
    }
  }

  private fun showVolume() {
    gestureListener?.let { gl ->
      val cur = gl.getVolumeCurrent()
      val min = gl.getVolumeMin()
      downVolume = cur
      gesture_volume_view.visible()
      gesture_volume_iv.setImageResource(if (cur == min) R.drawable.svg_media_mute else R.drawable.svg_media_volume_add)
      gesture_volume_tv.text = String.format("%d%%", (cur * 100).toInt())
    }
  }

  private fun showProgress() {
    gestureListener?.let { gl ->
      val cur = gl.getCurrentPosition()
      val max = gl.getDuration()
      downSeconds = cur / 1000
      gesture_seek_view.visible()
      gesture_seek_tv1.text = "+0s"
      gesture_seek_tv2.text = String.format(
          "%s/%s",
          VideoTimeUtils.instance.forMatterVideoTime(cur), VideoTimeUtils.instance.forMatterVideoTime(max)
      )
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="手势滑动修改UI">
  private var downSeconds: Long = 0
  private var downBright: Float = 0f
  private var downVolume: Float = 0f
  private var lastBright: Float = 0f
  private var lastVolume: Float = 0f

  private fun setBright(bright: Float) {
    if (bright > lastBright) gesture_bright_iv.setImageResource(R.drawable.svg_media_bright_add)
    else if (bright < lastBright) gesture_bright_iv.setImageResource(R.drawable.svg_media_bright_sub)
    gesture_bright_tv.text = String.format("%d%%", (bright * 100).toInt())
    lastBright = bright
    gestureListener?.setBright(bright)
  }

  private fun setVolume(volume: Float) {
    when {
      volume == gestureListener?.getVolumeMin() ?: 0f -> gesture_volume_iv.setImageResource(R.drawable.svg_media_mute)
      volume > lastVolume -> gesture_volume_iv.setImageResource(R.drawable.svg_media_volume_add)
      volume < lastVolume -> gesture_volume_iv.setImageResource(R.drawable.svg_media_volume_sub)
    }
    val volumePercent = volume / (gestureListener?.getVolumeMax() ?: 1f)
    gesture_volume_tv.text = String.format("%d%%", (volumePercent * 100).toInt())
    lastVolume = volume
    gestureListener?.setVolume(volumePercent)
  }

  private fun setSeekToPreView(msc: Long) {
    val currentSeconds = msc / 1000
    gesture_seek_tv1.text = String.format(
        "%s%s", if (currentSeconds >= downSeconds) "+" else "-",
        VideoTimeUtils.instance.forMatterVideoTimeSeek(abs(currentSeconds - downSeconds))
    )
    gesture_seek_tv2.text = String.format(
        "%s/%s", VideoTimeUtils.instance.forMatterVideoTime(currentSeconds * 1000),
        VideoTimeUtils.instance.forMatterVideoTime(gestureListener?.getDuration() ?: 0)
    )
    gestureListener?.seekPreviewTo(msc)
  }

  private fun setSeekToReally(msc: Long) {
    if (msc / 1000 != downSeconds) gestureListener?.seekTo(msc)
    gestureListener?.onStart()
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="手势滑动计算">
  private var newPositionVideo: Long = 0
  private fun onHorizontalSlide(percent: Float) {
    if (isLive) return //直播不能拖动进度
    gestureListener?.let { gl ->
      if (gesture_seek_view.visibility != View.VISIBLE) showProgress()
      //最大进度不能超过总时长的一半
      val duration = gl.getDuration()
      val maxChange = min(duration / 2, duration - downSeconds * 1000)
      newPositionVideo = max(0, min(duration, downSeconds * 1000 + (maxChange * percent).toLong()))
      setSeekToPreView(newPositionVideo)
    }
  }

  private fun onBrightVerticalSlide(percent: Float) {
    gestureListener?.let { gl ->
      if (gesture_bright_view.visibility != View.VISIBLE) showBright()
      setBright(max(gl.getBrightMin(), min(gl.getBrightMax(), downBright + gl.getBrightMax() * percent)))
    }
  }

  private fun onVolumeVerticalSlide(percent: Float) {
    gestureListener?.let { gl ->
      if (gesture_volume_view.visibility != View.VISIBLE) showVolume()
      setVolume(max(gl.getVolumeMin(), min(gl.getVolumeMax(), downVolume + gl.getVolumeMax() * percent)))
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="播放器回调">
  override fun setCall(call: VideoGestureListener) {
    gestureListener = call
  }

  private var mPlayState = PlayState.SET_DATA
  override fun callPlayState(state: PlayState) {
    mPlayState = state
    if (state == PlayState.SET_DATA || mPlayState == PlayState.PREPARING || mPlayState == PlayState.STOP
        || mPlayState == PlayState.COMPLETE || mPlayState == PlayState.ERROR) isLockScreen = false
    checkOperate()
  }

  private var isLockScreen = false
  override fun callUiState(uiState: PlayUiState) {
    when (uiState) {
      PlayUiState.LOCK_SCREEN -> isLockScreen = true
      PlayUiState.UNLOCK_SCREEN -> isLockScreen = false
      else -> {
      }
    }
    checkOperate()
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="设置操作状态">
  //根据当前播放状态和锁屏状态判断是否可以操作
  private fun checkOperate() {
    val canNot = (mPlayState == PlayState.SET_DATA || mPlayState == PlayState.SHOW_MOBILE || mPlayState == PlayState.PREPARING ||
        mPlayState == PlayState.BUFFING || mPlayState == PlayState.SEEKING || mPlayState == PlayState.STOP ||
        mPlayState == PlayState.COMPLETE || mPlayState == PlayState.ERROR)
    callOperate(!canNot && !isLockScreen)
  }

  //设置是否可以操作
  private fun callOperate(canOperate: Boolean) {
    canGestureVideo = canOperate
    if (!canOperate) {
      //请求父控件拦截
      this.requestDisallowInterceptTouchEvent(false)
      gesture_bright_view.gone()
      gesture_volume_view.gone()
      gesture_seek_view.gone()
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="手势回调">
  override fun callOverClick() {}

  override fun callOverDoubleClick() {}

  private var firstTouch = false
  private var horizontalSlide = false
  private var rightVerticalSlide = false
  override fun callOverScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float) {
    if (e1 != null && e2 != null && canGestureVideo && gestureListener != null) {
      val mOldX = e1.x
      val mOldY = e1.y
      val deltaY = mOldY - e2.y
      val deltaX = mOldX - e2.x
      if (firstTouch) {
        horizontalSlide = abs(distanceX) >= abs(distanceY)
        rightVerticalSlide = mOldX > width * 0.5f
        firstTouch = false
      }
      if (horizontalSlide) onHorizontalSlide(-deltaX / width)
      else {
        if (rightVerticalSlide) onVolumeVerticalSlide(deltaY / height)
        else onBrightVerticalSlide(deltaY / height)
      }
    }
  }

  override fun callOverTouchDown(e: MotionEvent?) {
    firstTouch = true
  }

  override fun callOverTouchUp() {
    if (gesture_seek_view.visibility == View.VISIBLE && canGestureVideo) setSeekToReally(newPositionVideo)
    firstTouch = false
    gesture_bright_view.gone()
    gesture_volume_view.gone()
    gesture_seek_view.gone()
  }
  //</editor-fold>
}