package com.example.klapproject

import android.widget.ImageView
import java.io.Serializable

data class ChatUser(val id:String, val content:String, val userImg:Int, val itemImg:Int): Serializable {
}