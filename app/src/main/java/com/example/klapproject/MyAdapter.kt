package com.example.klapproject


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyAdapter(fm: FragmentManager,val num:Int): FragmentPagerAdapter(fm){

    override fun getCount(): Int {
        return num
    }

    override fun getItem(position: Int): Fragment? {
         when(position){
            0-> return Fragment1()
            1-> return Fragment2()
             2->return Fragment1()
             3->return Fragment2()
        }
        return null
    }
}