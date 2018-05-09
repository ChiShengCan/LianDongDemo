package com.youyu.liandong.widget.indicator.slidebar

import android.view.View

/**
 * Created by csc on 2018/5/4.
 * explain：
 */
internal interface ScrollBar {

    val slideView: View

    val gravity: Gravity

    enum class Gravity {
        TOP,
        TOP_FLOAT,
        BOTTOM,
        BOTTOM_FLOAT,
        CENTENT,
        CENTENT_BACKGROUND
    }

    fun getHeight(tabHeight: Int): Int

    fun getWidth(tabWidth: Int): Int

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
}
