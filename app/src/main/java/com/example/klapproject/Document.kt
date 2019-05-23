package com.example.klapproject

data class Document(var d_title:String, var d_onoff:Boolean, //제목, 오프라인 온라인
                    var d_market:String, var d_info:String, // 구매장소 (혹은 링크), 상세정보
                    var d_gather:Int, var d_price:Int, //인원, 인당 예상가격
                    var d_place:String, var d_duty:Int) { // 만날 장소, 수수료
}