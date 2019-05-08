package com.example.klapproject

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var tabLayer:TabLayout?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init(){
        tabLayer = findViewById(R.id.layout_tab)
        tabLayer?.addTab(tabLayer!!.newTab().setText("홈"))
        tabLayer?.addTab(tabLayer!!.newTab().setText("글쓰기"))
        tabLayer?.addTab(tabLayer!!.newTab().setText("채팅"))
        tabLayer?.addTab(tabLayer!!.newTab().setText("마이 페이지"))
        // 탭 4개 추가

        val adapter = MyAdapter(supportFragmentManager, tabLayer!!.tabCount)
        content.adapter = adapter

        content.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayer))

        tabLayer!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e("test",tab.position.toString() +" "+content.currentItem.toString())
                content.currentItem = tab.position}
        })

    }
}
