package com.youyu.liandong

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import com.youyu.liandong.bean.SortDeatilBean

/**
 * Created by csc on 2018/5/9.
 * explain：实现对指示器Tab的切换
 */
class ItemHeaderDecoration(context: Context, private var mDatas: List<SortDeatilBean>?) : RecyclerView.ItemDecoration() {
    private val mTitleHeight: Int
    private val mInflater: LayoutInflater
    private var mCheckListener: CheckListener? = null

    private var currentTag: String? = "0"


    fun setCheckListener(checkListener: CheckListener) {
        mCheckListener = checkListener
    }

    init {
        val paint = Paint()
        mTitleHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, context.resources.displayMetrics).toInt()
        val titleFontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, context.resources.displayMetrics).toInt()
        paint.textSize = titleFontSize.toFloat()
        paint.isAntiAlias = true
        mInflater = LayoutInflater.from(context)
    }


    fun setData(mDatas: List<SortDeatilBean>): ItemHeaderDecoration {
        this.mDatas = mDatas
        return this
    }


    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val manager = parent.layoutManager as GridLayoutManager
        val spanSizeLookup = manager.spanSizeLookup
        val pos = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val spanSize = spanSizeLookup.getSpanSize(pos)
        Log.d("pos--->", pos.toString())
        var tag = mDatas!![pos].tag
        val child = parent.findViewHolderForLayoutPosition(pos).itemView
        var isTranslate = false//canvas是否平移的标志
        if (!TextUtils.equals(mDatas!![pos].tag, mDatas!![pos + 1].tag)
                || !TextUtils.equals(mDatas!![pos].tag, mDatas!![pos + 2].tag)
                || !TextUtils.equals(mDatas!![pos].tag, mDatas!![pos + 3].tag)) {
            tag = mDatas!![pos].tag
            val i = child.height + child.top

            if (spanSize == 1) {
                //body 才平移
                if (child.height + child.top < mTitleHeight) {
                    canvas.save()
                    isTranslate = true
                    val height = child.height + child.top - mTitleHeight
                    canvas.translate(0f, height.toFloat())
                }
            }

        }

        if (isTranslate) {
            canvas.restore()
        }

        if (!TextUtils.equals(tag, currentTag)) {
            currentTag = tag
            val integer = Integer.valueOf(tag!!)
            mCheckListener!!.check(integer, false)
        }
    }

    fun setCurrentTag(currentTag: String) {
       this.currentTag = currentTag
    }



}
