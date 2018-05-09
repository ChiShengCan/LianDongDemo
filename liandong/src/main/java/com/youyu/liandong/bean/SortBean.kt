package com.youyu.liandong.bean

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by csc on 2018/5/8.
 * explain：分类数据
 */
class SortBean : Parcelable {
    var name: String? = null
    var tag: String? = null
    private var isTitle: Boolean = false
    var imgsrc: String? = null

    var titleName: String? = null

    var title: String? = null

    var categoryOneArray: ArrayList<CategoryOneArrayBean>? = null


    constructor(name: String) {
        this.name = name
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        tag = `in`.readString()
        isTitle = `in`.readByte().toInt() != 0
    }

    fun isTitle(): Boolean {
        return isTitle
    }

    fun setTitle(title: Boolean) {
        isTitle = title
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(tag)
        dest.writeByte((if (isTitle) 1 else 0).toByte())
        dest.writeString(imgsrc)
        dest.writeString(titleName)
        dest.writeString(title)
        dest.writeTypedList(categoryOneArray)
    }


    class CategoryOneArrayBean protected constructor(`in`: Parcel) : Parcelable {

        var name: String? = null
        var imgsrc: String? = null
        var cacode: String? = null
        var categoryTwoArray: List<CategoryTwoArrayBean>? = null

        init {
            name = `in`.readString()
            imgsrc = `in`.readString()
            cacode = `in`.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(name)
            dest.writeString(imgsrc)
            dest.writeString(cacode)
        }

        override fun describeContents(): Int {
            return 0
        }

        class CategoryTwoArrayBean protected constructor(`in`: Parcel) : Parcelable {


            var name: String? = null
            var imgsrc: String? = null
            var cacode: String? = null

            init {
                name = `in`.readString()
                imgsrc = `in`.readString()
                cacode = `in`.readString()
            }

            override fun describeContents(): Int {
                return 0
            }

            override fun writeToParcel(dest: Parcel, flags: Int) {
                dest.writeString(name)
                dest.writeString(imgsrc)
                dest.writeString(cacode)
            }

            companion object {

                val CREATOR: Parcelable.Creator<CategoryTwoArrayBean> = object : Parcelable.Creator<CategoryTwoArrayBean> {
                    override fun createFromParcel(`in`: Parcel): CategoryTwoArrayBean {
                        return CategoryTwoArrayBean(`in`)
                    }

                    override fun newArray(size: Int): Array<CategoryTwoArrayBean?> {
                        return arrayOfNulls(size)
                    }
                }
            }
        }

        companion object {

            val CREATOR: Parcelable.Creator<CategoryOneArrayBean> = object : Parcelable.Creator<CategoryOneArrayBean> {
                override fun createFromParcel(`in`: Parcel): CategoryOneArrayBean {
                    return CategoryOneArrayBean(`in`)
                }

                override fun newArray(size: Int): Array<CategoryOneArrayBean?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {


        val CREATOR: Parcelable.Creator<SortBean> = object : Parcelable.Creator<SortBean> {
            override fun createFromParcel(`in`: Parcel): SortBean {
                return SortBean(`in`)
            }

            override fun newArray(size: Int): Array<SortBean?> {
                return arrayOfNulls(size)
            }
        }
    }


}
