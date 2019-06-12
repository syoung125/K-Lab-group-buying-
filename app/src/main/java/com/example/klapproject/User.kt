package com.example.klapproject

import java.io.Serializable

data class User(var id: String,
                var password:String,
                var univ:String,
                var name:String,
                var place:String,
                var score:Double,
                var alarm_list:ArrayList<String> = arrayListOf(),
                var review:ArrayList<String> = arrayListOf(),
                var tran_list:ArrayList<String> = arrayListOf(),
                var upload_list:ArrayList<String> = arrayListOf()
                ) : Serializable {
}