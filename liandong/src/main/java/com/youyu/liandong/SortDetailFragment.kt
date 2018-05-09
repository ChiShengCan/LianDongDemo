package com.youyu.liandong


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.youyu.liandong.bean.SortBean
import com.youyu.liandong.bean.SortDeatilBean



/**
 * A simple [Fragment] subclass.
 *
 */
class SortDetailFragment : Fragment() {



    //实体类
    var mDatas:MutableList<SortDeatilBean> = ArrayList<SortDeatilBean>()

    //条目选中监听
    var mCheckListener:CheckListener?=null

    lateinit var mDecoration:ItemHeaderDecoration




    companion object {

        lateinit var mRv:RecyclerView
        lateinit var mAdapter:SortDetailAdapter
        lateinit var mManger:GridLayoutManager

        //控住滚动标识
        private var move:Boolean=false

        private var mIndex:Int=0


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var  view:View=inflater.inflate(R.layout.fragment_sort_detail, container, false)

        mRv=view.findViewById(R.id.rv) as RecyclerView

        initRv()
        initData()
        return view
    }



    private fun initRv() {
        mManger= GridLayoutManager(context,4)

        //根据是否是Title判断1列还是3列
        mManger.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                //因为我们添加了一个Footer，所以这里需要添加一个判断
                return if (position < mDatas.size) {
                    if (mDatas.get(position).isTitle) 4 else 1
                } else {
                    //多余的一个位置是Footer
                    4
                }
            }
        })

        mRv.layoutManager=mManger

        //设置适配器和条目点击监听
        mAdapter= SortDetailAdapter(context!!,mDatas,object :RvListener{
            override fun onItemClick(id: Int, position: Int) {
                Toast.makeText(context, "当前点击到的位置:" + position + ",点击到的内容:" + mDatas.get(position).name, Toast.LENGTH_SHORT).show()
            }

        })

        //设置适配器
        mRv.adapter=mAdapter

        //结合Decoration实现当滚动Rv的时候对指示器Tab的切换的切换
        mDecoration= ItemHeaderDecoration(context!!,mDatas)
        mRv.addItemDecoration(mDecoration)
        //添加选中条目的监听，实现指示器Tab的切换
        mDecoration.setCheckListener(mCheckListener!!)

        //添加滚动监听
        mRv.addOnScrollListener(RecyclerViewListener())
    }

    //获取数据
    private fun initData() {

        //Fragment的Argument
        var lists:ArrayList<SortBean.CategoryOneArrayBean> = arguments?.getParcelableArrayList("bean")!!

        for (i in 0 until lists.size){

            //设置标题的数据
            var head:SortDeatilBean= SortDeatilBean(lists.get(i).name!!)
            head.isTitle=true
            head.titleName=lists.get(i).name
            //给Title标识一下它的位置
            head.tag=i.toString()
            mDatas.add(head)

            var listTwo:ArrayList<SortBean.CategoryOneArrayBean.CategoryTwoArrayBean> = lists.get(i).categoryTwoArray as ArrayList<SortBean.CategoryOneArrayBean.CategoryTwoArrayBean>

            for (j in 0 until listTwo.size){
                //设置详情类的标题
                var body:SortDeatilBean = SortDeatilBean(listTwo.get(j).name!!)

                body.tag=i.toString()

                body.titleName=lists.get(i).name

                mDatas.add(body)

            }

        }

        //更新适配器
        mAdapter.notifyDataSetChanged()


    }

    //设置适配器的条目点击监听
    public fun setListener(listener:CheckListener){
        this.mCheckListener=listener
    }
    //设置选中的位置
    public fun setPosition(n:Int){
        mIndex=n
        mRv.stopScroll()
        //移动到对应的条目
        smoothMoveToPosition(n)

    }

    //移动到对应的条目
    private fun smoothMoveToPosition(n: Int) {
        //第一个可见条目的位置
        var firstItem:Int= mManger.findFirstVisibleItemPosition()
        //最后可见可见条目的位置
        var lastItem= mManger.findLastVisibleItemPosition()

        if (n<=firstItem){
            mRv.scrollToPosition(n)
        }else if (n<=lastItem){
            var top:Int= mRv.getChildAt(n-firstItem).top
            mRv.scrollBy(0,top)
        }else{
            mRv.scrollToPosition(n)
            move=true
        }
    }


    //Rv滚动监听
    class RecyclerViewListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE){
                move=false

                var p= mIndex - mManger.findFirstVisibleItemPosition()

                if (0<=p && p< mRv.childCount){

                    //得到当前条目 距离 顶部的距离
                    var top:Int= mRv.getChildAt(p).top

                    //移动条目
                    mRv.smoothScrollBy(0,top)
                }
            }
        }


        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (move){
                move=false
                var p= mIndex- mManger.findFirstVisibleItemPosition()
                if (0<=p && p<mRv.getChildAt(p).top){
                    var top:Int= mRv.getChildAt(p).top
                    mRv.scrollBy(0,top)
                }
            }
        }
    }


}


