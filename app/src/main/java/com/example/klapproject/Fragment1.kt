package com.example.klapproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fragment1.view.*

class Fragment1 : Fragment() {

    interface makeIntent{
        fun makeIntent(num:Int);
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)

        view.imgButton1.setOnClickListener {
            if(activity is makeIntent)
                (activity as makeIntent).makeIntent(1)
        }
        view.imgButton2.setOnClickListener {
            if(activity is makeIntent)
                (activity as makeIntent).makeIntent(2)
        }
        view.imgButton3.setOnClickListener {
            if(activity is makeIntent)
                (activity as makeIntent).makeIntent(3)
        }
        view.imgButton4.setOnClickListener {
            if(activity is makeIntent)
                (activity as makeIntent).makeIntent(4)
        }
        view.imgButton5.setOnClickListener {
            if(activity is makeIntent)
                (activity as makeIntent).makeIntent(5)
        }
        view.imgButton6.setOnClickListener {
            if(activity is makeIntent)
                (activity as makeIntent).makeIntent(6)
        }

        return view
    }
}
