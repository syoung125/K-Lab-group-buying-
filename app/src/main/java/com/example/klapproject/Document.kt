package com.example.klapproject

import android.util.Log
import java.time.LocalDateTime

data class Document(var d_postnickname:String) {
    //인자: 게시자
    var d_now:LocalDateTime? = null   //현재날짜
    var d_url:String? = null    //사진
    var d_title:String? = null  //제목
    var d_category:Int? = null  //카테고리
    var d_on:Boolean = false    //온라인
    var d_off:Boolean = false   //오프라인
    var d_market:String? = null //구매 예상 상세정보
    var d_info:String? = null //상세정보
    var d_num:Int? = null //인원
    var d_price:Int? = null //인당 가격
    var d_place:String? = null // 만날 장소
    var d_duty:Int? = null //수수료

    fun is_empty():Boolean{
        Log.v("Document", d_postnickname+": "+d_url+", "+d_title+", "+d_category.toString()+", "+d_market+", "+d_info +", "+ d_num.toString()+", "+
                d_price +", "+d_place +", "+d_duty)
        return (d_url == null || d_title == null || d_category == null || d_market == null || d_info == null || d_num == null ||
            d_price == null || d_place == null || d_duty == null)
    }

//    fun is_empty():Boolean{
//        return !(d_url != null && d_title != null && d_category != null && d_market != null && d_info != null && d_num != null &&
//                d_price != null && d_place != null && d_duty != null)
//    }
}