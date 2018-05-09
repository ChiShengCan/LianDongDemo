package com.youyu.liandong

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.youyu.liandong.bean.SortDeatilBean

/**
 * Created by csc on 2018/5/8.
 * explain：分类详情适配器
 */
class SortDetailAdapter(context: Context,beanList:List<SortDeatilBean>,listener:RvListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mContext:Context?=null
    lateinit var mBeans:List<SortDeatilBean>
    var layoutInflater:LayoutInflater?=null

    companion object {
        lateinit var mRvListener: RvListener
    }
    init {
        mContext=context
        mBeans=beanList
        mRvListener=listener
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getItemViewType(position: Int): Int {
        if (position<mBeans.size){
            if (mBeans.get(position).isTitle){
                return 0
            }else if (!mBeans.get(position).isTitle){
                return 1
            }
        }

        //这个多余的是footer(说明一下为什么要添加这个Footer呢，因为当条目没有数据的时候，rv是不能再向上滚动的，当我们点击到左边的item的时候，右边的Rv最后的类型不能置顶，加个Footer为了保证最后那个条目能够置顶)
        //如果不需要最后那个条目置顶的话，请不要加Footer(注意没有footer需修改:getItemCount()的总数,修改SortDetailFragment中的 initRv())
        return 2

    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType ==0){
            return TitleViewHoder(layoutInflater?.inflate(R.layout.item_sort_title,parent,false))
        }else if (viewType == 1){
            return ContentViewHolder(layoutInflater?.inflate(R.layout.item_sort_content,parent,false))
        }else{
            //这里Footer高度写死了，需要动态设置比较好
            return FooterViewHolder(layoutInflater?.inflate(R.layout.item_sort_footer, parent, false))
        }
    }

    override fun getItemCount(): Int {
        //我们需要添加一个Footer，所以总条目+1
        return if (mBeans == null) 0 else mBeans.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is TitleViewHoder){
            //设置标题数据
            (holder as TitleViewHoder).title.text=mBeans.get(position).name
        }else if (holder is ContentViewHolder){
            //设置内容数据
            (holder as ContentViewHolder).contentTv.text=mBeans.get(position).name
        }
    }

    //标题布局
    class TitleViewHoder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        //标题
        lateinit var title:TextView
        //颜色分割线
        lateinit var view: View
        init {

            title=itemView?.findViewById(R.id.tv_title_item) as TextView
            view=itemView?.findViewById(R.id.div_title_item) as View

          //条目点击监听
            itemView?.setOnClickListener{
                mRvListener.onItemClick(it.id,adapterPosition)
            }

        }
    }

    //内容布局
    class ContentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        lateinit var contentTv:TextView
        init {
            contentTv=itemView?.findViewById(R.id.tvContent) as TextView

            //条目点击监听
            itemView.setOnClickListener{
                mRvListener.onItemClick(it.id,adapterPosition)
            }
        }
    }

    //Footer布局
    class FooterViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }


}
