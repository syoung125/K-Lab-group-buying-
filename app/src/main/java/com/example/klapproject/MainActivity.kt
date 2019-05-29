package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

var doc_list:MutableList<Document> = mutableListOf()
var u_nickname:String = "user123"

class MainActivity : AppCompatActivity()/*,Fragment1.makeIntent*/ {

//    override fun makeIntent(num: Int) {
//        val i = Intent(this, CategoryActivity::class.java)
//        // 명시적으로 원하는 창 설정을 만듦
//        i.putExtra("Category", num)
//        // 단일 값이 아닌 객체를 만들어 넘기고 싶음
//        startActivity(i)
//        // 해당 intent 를 실행
//    }

    private var tabLayer:TabLayout?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var longinintent = Intent(this, LoginActivity::class.java)
        startActivityForResult(longinintent,1)

        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
          if(resultCode == Activity.RESULT_CANCELED)
            Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
        }
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
                Log.e("test", tab.position.toString() + " " + content.currentItem.toString())
                if(tab.position==1){
                    var next = Intent(applicationContext, WritingForm::class.java)
                    startActivity(next)
                }
                content.currentItem = tab.position
            }
        })

    }
}
