package com.cc.video.inter

/**
 * 播放异常的重新播放操作
 * Author:CASE
 * Date:2020-9-19
 * Time:10:20
 */
interface VideoErrorListener {
  fun continuePlay()
  fun resetPlay()
}