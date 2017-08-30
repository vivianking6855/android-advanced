package com.open.kotlinstart

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    internal lateinit var mList: ListView  // 对于非空类型的属性是必须初始化的，如果希望延迟进行初始化就可以用该关键字。lateinit只能在不可null的对象上使用，比须为var，不能为primitives（Int、Float之类

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mList = findViewById(R.id.mList) as ListView
        val customAdapter = CustomAdapter(this, createTestData())
        mList.adapter = customAdapter
    }

    internal fun createTestData(): List<String> { //在本模块之内, 凡是能够访问到这个类的地方, 同时也能访问到这个类的 internal 成员
        val data = ArrayList<String>()
        for (i in 0..49) {
            data.add("Love World " + i)
        }
        return data
    }

    internal inner class CustomAdapter(context: Context, var mData: List<String>?) : BaseAdapter() {
        //加“?”和不加的根本区别在哪？就在于程序运行过程中对变量的赋值，如果给没有加“?”的变量赋值了null，程序就会异常。
        var mInflater: LayoutInflater

        init {
            mInflater = LayoutInflater.from(context)
        }

        override fun getCount(): Int {
            if (mData == null || mData!!.size <= 0) {//!!可以为null
                return 0
            }
            return mData!!.size
        }

        override fun getItem(position: Int): Any? {
            if (mData == null || mData!!.size <= 0 || position < 0 || position >= mData!!.size) {//
                return null
            }
            return mData!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null)
            }

            val name = convertView!!.findViewById(R.id.tv_name) as TextView
            name.text = getItem(position) as CharSequence?

            return convertView
        }
    }
}
