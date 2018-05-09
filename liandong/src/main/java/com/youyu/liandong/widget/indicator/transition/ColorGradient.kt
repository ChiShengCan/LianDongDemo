package com.youyu.liandong.widget.indicator.transition

import android.graphics.Color

/**
 * Created by csc on 2018/5/4.
 * explain：用于获取两个颜色的过渡色
 */
internal class ColorGradient(
        /** 开始的颜色  */
        val color1: Int,
        /** 结束的颜色  */
        val color2: Int,
        /** 开始的颜色到结束的颜色的过渡色分为几份  */
        val count: Int) {
    /** 开始的颜色的a，r，g，b值  */
    private val color1Values: IntArray
    /** 结束的颜色的a，r，g，b值  */
    private val color2Values: IntArray

    init {
        color1Values = toColorValue(color1)
        color2Values = toColorValue(color2)
    }

    /**
     * 获取第几个过渡色，总共分为count个过渡色，i表示去其中的第i个过渡色
     *
     * @param i
     * @return
     */
    fun getColor(i: Int): Int {
        val result = IntArray(4)
        for (j in color2Values.indices) {
            result[j] = (color1Values[j] + (color2Values[j] - color1Values[j]) * 1.0 / count * i).toInt()
        }
        return Color.argb(result[0], result[1], result[2], result[3])
    }

    private fun toColorValue(color: Int): IntArray {
        val values = IntArray(4)
        values[0] = Color.alpha(color)
        values[1] = Color.red(color)
        values[2] = Color.green(color)
        values[3] = Color.blue(color)
        return values
    }

}
