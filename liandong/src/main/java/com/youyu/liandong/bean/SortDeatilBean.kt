package com.youyu.liandong.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by csc on 2018/5/8.
 * explain：分类详情实体
 */
class SortDeatilBean : Parcelable {
    var name: String? = null
    var titleName: String? = null
    var tag: String? = null
    var isTitle: Boolean = false
    var imgsrc: String? = null

    constructor(name: String) {
        this.name = name
    }

    protected constructor(`in`:Parcel) {
        name = `in`.readString()
        titleName = `in`.readString()
        tag = `in`.readString()
        isTitle = `in`.readByte().toInt() != 0
        imgsrc = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(titleName)
        dest.writeString(tag)
        dest.writeByte((if (isTitle) 1 else 0).toByte())
        dest.writeString(imgsrc)
    }

    companion object {

        val CREATOR: Parcelable.Creator<SortDeatilBean> = object : Parcelable.Creator<SortDeatilBean> {
            override fun createFromParcel(`in`: Parcel): SortDeatilBean {
                return SortDeatilBean(`in`)
            }

            override fun newArray(size: Int): Array<SortDeatilBean?> {
               return arrayOfNulls(size)
            }
        }
    }
}
