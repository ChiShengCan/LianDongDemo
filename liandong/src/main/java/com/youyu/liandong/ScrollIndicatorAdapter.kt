package com.youyu.liandong

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.youyu.liandong.utils.DensityUtil
import com.youyu.liandong.widget.indicator.Indicator


/**
 * Created by csc on 2018/5/7.
 * explain:指示器的适配器(加载指示器的布局)
 */

class ScrollIndicatorAdapter : Indicator.IndicatorAdapter{

    var count:Int?=null
    var titles:List<String>?=null
    var mContext:Context?=null

    constructor(lists:List<String>,context: Context){
        titles=lists
        mContext=context

    }

    override fun getCount(): Int {
        return titles?.size?:0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View
        if (convertView == null){
            var layoutInflater=LayoutInflater.from(mContext)
            view= layoutInflater?.inflate(R.layout.tab,parent,false)!!

        }else{
            view=convertView
        }

        var textView:TextView= view as TextView
        //设置Tab的宽度
        textView.width= DensityUtil.dip2px(mContext!!,80F)
        textView.text=titles?.get(position).toString()

        return view



    }

}
