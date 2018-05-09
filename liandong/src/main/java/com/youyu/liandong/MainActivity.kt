package com.youyu.liandong

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.youyu.liandong.bean.SortBean
import com.youyu.liandong.widget.indicator.slidebar.ColorBar
import com.youyu.liandong.widget.indicator.transition.OnTransitionTextListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), CheckListener {

    //分享详情布局
    lateinit var mSortDetailFragment: SortDetailFragment
    //记录指示器选中的位置
    private var targetPosition: Int = 0
    private var isMove: Boolean = false

    //数据实体
    var mSortBean: SortBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()

    }


    private fun initIndicator(list: ArrayList<String>) {
        /***************设置指示器****************/
        //设置选中和未选中文字的颜色
        indicator.setOnTransitionListener(OnTransitionTextListener().setColor(resources.getColor(R.color.buttonSelectColor), Color.BLACK).setSize(14.0F, 12.0F))
        //设置指示器的颜色、宽度、高度
        indicator.setScrollBar(ColorBar(this, resources.getColor(R.color.buttonSelectColor), 100, 3))
        //设置默认的选中条目
        indicator.setCurrentItem(1, true)
        //给指示器设置数据 ScrollIndicatorAdapter
        indicator.setAdapter(ScrollIndicatorAdapter(list, this))

        /***************指示器设置条目选中监听********************/
        indicator.setOnItemSelectListener { selectItemView, select, preSelect ->

            isMove = true
            targetPosition = select

            //设置选中监听(布尔值表示是否是标题栏点击)
            setChecked(select, true)

        }




    }

    //创建分类详情布局
    private fun createFragment() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        mSortDetailFragment = SortDetailFragment()
        val bundle: Bundle = Bundle()
        bundle.putParcelableArrayList("bean", mSortBean?.categoryOneArray)
        mSortDetailFragment.arguments=bundle
        //设置条目点击监听
        mSortDetailFragment.setListener(this)

        fragmentTransaction.add(R.id.content_sort, mSortDetailFragment)
        fragmentTransaction.commit()

    }

    override fun check(position: Int, isScroll: Boolean) {
        setChecked(position, isScroll)
    }

    private fun setChecked(position: Int, isTitle: Boolean) {
        if (!isTitle){
            if (isMove){
                isMove=false
            }else{
                //指示器跳转到对应的条目
                indicator.setCurrentItem(position, true)
            }
        }else{
            //表示点击到指示器
            var count:Int=0
            for (i in 0 until position){
                count+= mSortBean?.categoryOneArray?.get(i)?.categoryTwoArray?.size!!
            }
            count+=position

            //详情页滚动到对应的位置
            mSortDetailFragment.setPosition(count)
        }
    }


    private fun initData() {
        //获取asset目录下的资源文件
        val assetsData = getAssetsData("sort.json")
        val gson = Gson()
        mSortBean = gson.fromJson(assetsData, SortBean::class.java)

        val categoryOneArray = mSortBean?.categoryOneArray

        //获取标题的数据
        val list = ArrayList<String>()
        for (i in 0 until categoryOneArray?.size!!) {
            list.add(categoryOneArray?.get(i)?.name!!)
        }


        //实例化指示器数据
        initIndicator(list)
        //创建分类详情布局
        createFragment()


    }


    fun getAssetsData(path: String): String {
        var result = ""
        try {
            //获取输入流
            val mAssets = assets.open(path)
            //获取文件的字节数
            val lenght = mAssets.available()
            //创建byte数组
            val buffer = ByteArray(lenght)
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer)
            mAssets.close()
            result = String(buffer)
            return result
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("error", e.message)
            return result
        }


    }
}




