package com.youyu.liandong.widget.indicator.transition

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.youyu.liandong.widget.indicator.Indicator

/**
 * Created by csc on 2018/5/4.
 * explain：
 */
internal class OnTransitionTextListener : Indicator.OnTransitionListener {
    private var selectSize = -1f
    private var unSelectSize = -1f
    private var gradient: ColorGradient? = null
    private var dFontFize = -1f

    private var isPxSize = false

    constructor() : super() {}

    constructor(selectSize: Float, unSelectSize: Float, selectColor: Int, unSelectColor: Int) : super() {
        setColor(selectColor, unSelectColor)
        setSize(selectSize, unSelectSize)
    }

    fun setSize(selectSize: Float, unSelectSize: Float): OnTransitionTextListener {
        isPxSize = false
        this.selectSize = selectSize
        this.unSelectSize = unSelectSize
        this.dFontFize = selectSize - unSelectSize
        return this
    }

    fun setValueFromRes(context: Context, selectColorId: Int, unSelectColorId: Int, selectSizeId: Int,
                        unSelectSizeId: Int): OnTransitionTextListener {
        setColorId(context, selectColorId, unSelectColorId)
        setSizeId(context, selectSizeId, unSelectSizeId)
        return this
    }

    fun setColorId(context: Context, selectColorId: Int, unSelectColorId: Int): OnTransitionTextListener {
        val res = context.resources
        setColor(res.getColor(selectColorId), res.getColor(unSelectColorId))
        return this
    }

    fun setSizeId(context: Context, selectSizeId: Int, unSelectSizeId: Int): OnTransitionTextListener {
        val res = context.resources
        setSize(res.getDimensionPixelSize(selectSizeId).toFloat(), res.getDimensionPixelSize(unSelectSizeId).toFloat())
        isPxSize = true
        return this
    }

    fun setColor(selectColor: Int, unSelectColor: Int): OnTransitionTextListener {
        gradient = ColorGradient(unSelectColor, selectColor, 100)
        return this
    }

    /**
     * 如果tabItemView 不是目标的TextView，那么你可以重写该方法返回实际要变化的TextView
     *
     * @param tabItemView
     * Indicator的每一项的view
     * @param position
     * view在Indicator的位置索引
     * @return
     */
    fun getTextView(tabItemView: View, position: Int): TextView {
        return tabItemView as TextView
    }

    override fun onTransition(view: View, position: Int, selectPercent: Float) {
        val selectTextView = getTextView(view, position)
        if (gradient != null) {
            selectTextView.setTextColor(gradient!!.getColor((selectPercent * 100).toInt()))
        }
        if (unSelectSize > 0 && selectSize > 0) {
            if (isPxSize) {
                selectTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, unSelectSize + dFontFize * selectPercent)
            } else {
                selectTextView.textSize = unSelectSize + dFontFize * selectPercent
            }

        }
    }

}
