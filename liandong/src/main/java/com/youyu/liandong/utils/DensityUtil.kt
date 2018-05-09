package com.youyu.liandong.utils

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * Created by csc on 2018/5/8.
 * explain:屏幕工具
 */
class DensityUtil{

   companion object {
       private var screenWidth = 0
       private val screenHeight = 0

       fun dip2px(var0: Context, var1: Float): Int {
           val var2 = var0.resources.displayMetrics.density
           return (var1 * var2 + 0.5f).toInt()
       }

       fun px2dip(var0: Context, var1: Float): Int {
           val var2 = var0.resources.displayMetrics.density
           return (var1 / var2 + 0.5f).toInt()
       }

       fun sp2px(var0: Context, var1: Float): Int {
           val var2 = var0.resources.displayMetrics.scaledDensity
           return (var1 * var2 + 0.5f).toInt()
       }

       fun px2sp(var0: Context, var1: Float): Int {
           val var2 = var0.resources.displayMetrics.scaledDensity
           return (var1 / var2 + 0.5f).toInt()
       }

       fun getScreenWidth(c: Context): Int {
           if (screenWidth == 0) {
               val wm = c.getSystemService(Context.WINDOW_SERVICE) as WindowManager
               val display = wm.defaultDisplay
               val size = Point()
               display.getSize(size)
               screenWidth = size.x
           }
           return screenWidth
       }

       /**
        * 设置某个View的margin
        *
        * @param view   需要设置的view
        * @param isDp   需要设置的数值是否为DP
        * @param left   左边距
        * @param right  右边距
        * @param top    上边距
        * @param bottom 下边距
        * @return
        */
       fun setViewMargin(view: View?, isDp: Boolean, left: Int, right: Int, top: Int, bottom: Int): ViewGroup.LayoutParams? {
           if (view == null) {
               return null
           }

           var leftPx = left
           var rightPx = right
           var topPx = top
           var bottomPx = bottom
           val params = view.layoutParams
           var marginParams: ViewGroup.MarginLayoutParams? = null
           //获取view的margin设置参数
           if (params is ViewGroup.MarginLayoutParams) {
               marginParams = params
           } else {
               //不存在时创建一个新的参数
               marginParams = ViewGroup.MarginLayoutParams(params)
           }

           //根据DP与PX转换计算值
           if (isDp) {
               leftPx = dip2px(left.toFloat(), null!!)
               rightPx = dip2px(right.toFloat(), null!!)
               topPx = dip2px(top.toFloat(), null!!)
               bottomPx = dip2px(bottom.toFloat(), null!!)
           }
           //设置margin
           marginParams.setMargins(leftPx, topPx, rightPx, bottomPx)
           view.layoutParams = marginParams
           view.requestLayout()
           return marginParams
       }

       fun dip2px(dpValue: Float, context: Context): Int {
           val scale = context.resources.displayMetrics.density
           return (dpValue * scale + 0.5f).toInt()
       }
   }
}