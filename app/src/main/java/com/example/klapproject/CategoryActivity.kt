package com.example.klapproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    lateinit var list:MutableList<Document>
    lateinit var categoryAdapter:CategoryAdapter
    var division:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        init()
    }

    fun init(){

        division = intent!!.getIntExtra("Category",-1)

        list = mutableListOf()

        list.add(Document("휴지 사실 분 구해요~",false,
            "근처 마켓","휴지 큰거 하나 사서 10개씩 나눠서 가져가요~",3,1000,
            "xx동",200))
        list.add(
            Document("1+1 스타킹 구매",true,
                "G마켓","스타킹 1+1 행사중이예요",1,2500,
                "보고 결정해요",500))
        list.add(Document("후라보노 치약 대량으로",false,
            "동네 슈퍼","싸서 샀는데 너무 많이 사서 팔아요. 관심있으면 연락",3,2000,
            "상의",0))

        categoryAdapter = CategoryAdapter(this,R.layout.category_form,list)

        category_list.adapter = categoryAdapter
    }
}
