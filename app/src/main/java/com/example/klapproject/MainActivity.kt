package com.example.klapproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity()/*,Fragment1.makeIntent*/ {

    companion object{
        var u_nickname:String? = "user123"
        var u_num:Int?=null
        var doc_list:MutableList<Document> = mutableListOf()
    }


    private var tabLayer:TabLayout?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
          if(resultCode == Activity.RESULT_OK) {
              u_num = data?.getIntExtra("user", -1)
              u_nickname = data?.getStringExtra("nick")
//              currentUser = data?.getStringExtra("userid")
//              Toast.makeText(applicationContext, "currentUser: ${currentUser}", Toast.LENGTH_SHORT).show()
              Log.e("메인", "$u_num\t$u_nickname")
          }
            else
              finish()
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
