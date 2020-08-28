package com.cc.base2021.widget.picsel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.ImageRequest
import coil.util.CoilUtils
import com.blankj.utilcode.util.Utils
import com.cc.base2021.ext.loadFullScreen
import com.cc.base2021.ext.loadImg
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.tools.MediaUtils
import com.luck.picture.lib.widget.longimage.*
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

/**
 * Author:case
 * Date:2020/8/28
 * Time:15:32
 */
class ImageEngine : com.luck.picture.lib.engine.ImageEngine {
  //加载图片
  override fun loadImage(context: Context, url: String, imageView: ImageView) {
    imageView.loadFullScreen(url)
  }

  //加载网络图片适配长图方案(此方法只有加载网络图片才会回调)
  override fun loadImage(
    context: Context,
    url: String,
    imageView: ImageView,
    longImageView: SubsamplingScaleImageView?,
    callback: OnImageCompleteCallback?
  ) {
    var newUrl = url
    //先判断是否有缓存
    url.toHttpUrlOrNull()?.let { u ->
      val f = CoilUtils.createDefaultCache(Utils.getApp()).directory.listFiles().orEmpty().find { it.name.contains(Cache.key(u)) }
      if (f?.exists() == true) { //文件存在直接加载
        newUrl = Uri.fromFile(f).toString()
      }
    }
    Coil.imageLoader(Utils.getApp()).enqueue(
      ImageRequest.Builder(Utils.getApp()).data(newUrl).target(
        onStart = {
          callback?.onShowLoading()
        },
        onSuccess = { resource ->
          loadNetImage(resource.toBitmap(), imageView, longImageView)
          callback?.onHideLoading()
        },
        onError = {
          callback?.onHideLoading()
        }
      ).build()
    )
  }

  //加载网络图片
  private fun loadNetImage(bitmap: Bitmap, imageView: ImageView, longImageView: SubsamplingScaleImageView?) {
    val eqLongImage: Boolean = MediaUtils.isLongImg(bitmap.width, bitmap.height)
    longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
    imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
    if (eqLongImage) {
      // 加载长图
      longImageView?.apply {
        isQuickScaleEnabled = true
        isZoomEnabled = true
        isPanEnabled = true
        setDoubleTapZoomDuration(100)
        setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
        setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
        setImage(ImageSource.bitmap(bitmap), ImageViewState(0f, PointF(0f, 0f), 0))
      }
    } else {
      // 普通图片
      imageView.setImageBitmap(bitmap)
    }
  }

  //已废弃
  override fun loadImage(context: Context, url: String, imageView: ImageView, longImageView: SubsamplingScaleImageView?) {
    loadImage(context, url, imageView, longImageView, null)
  }

  //加载相册目录
  override fun loadFolderImage(context: Context, url: String, imageView: ImageView) {
    imageView.loadImg(url)
  }

  //加载gif
  override fun loadAsGifImage(context: Context, url: String, imageView: ImageView) {
  }

  //加载图片列表图片
  override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
    imageView.loadImg(url)
  }
}