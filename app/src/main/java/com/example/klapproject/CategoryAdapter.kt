package com.example.klapproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class CategoryAdapter (context: Context, val resource:Int, var list:MutableList<Document>)
    // 객체 생성될 때 전달되는 값 Context
    // Layout의 id 정보 (int), 정보를 가진 배열 ArrayList 을 받는 생성자
        : ArrayAdapter<Document>(context,resource,list)
    // ArrayAdapter를 받을 경우
    // 부모 생성자에겐 3개의 인자전달이 필요
    {
        // ctrl + O = 오버라이딩 가능한 함수 목록
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var v: View? = convertView
            // 이미 만들어진 뷰가 있는 경우, 기존 뷰의 정보가 들어오고, 정보 수정
            // 처음인 경우, null 값이 들어옴 (view를 생성해줘야 함)
            if (v == null) { //만들어진 view가 없다면
                val vi = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                // layout 객체를 받아온 것과 동일
                v = vi.inflate(resource, null)
                // 레이아웃 정보와 루트로 사용 여부
            }

            val p = list.get(position)
            v!!.findViewById<ImageView>(R.id.c_Img)
            v!!.findViewById<TextView>(R.id.c_title).text = p.d_title
            v!!.findViewById<TextView>(R.id.c_onoff).text = if(p.d_onoff) "온라인"
                                                            else "오프라인"
            v!!.findViewById<TextView>(R.id.c_person).text = p.d_gather.toString() + " 명"
            v!!.findViewById<TextView>(R.id.c_money).text = p.d_price.toString() + " 원"

            // 리스트 뷰에 붙는 모든 놈들이 하나의 객체이므로
            // 각각에 대해서 리스너를 달아줘야함

            return v // 만들었으니 만든거 return
            //return super.getView(position, convertView, parent)
        }
    }