package com.example.klapproject

import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.time.LocalDateTime
import java.util.*
import android.support.annotation.NonNull
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.Serializable
import java.time.Instant.now


data class Document(var d_postnickname:String?): Serializable {
    //인자: 게시자
    var d_now:LocalDateTime? = null   //현재날짜
//    var uri:Uri ?=null   //URI
    var d_url:String? = null    //사진
    var d_title:String? = null  //제목
    var d_category:String? = null  //카테고리
    var d_on:Boolean = false    //온라인
    var d_off:Boolean = false   //오프라인
    var d_market:String? = null //구매 예상 상세정보
    var d_info:String? = null //상세정보
    var d_num:String? = null //인원
    var d_price:String? = null //인당 가격
    var d_place:String? = null // 만날 장소
    var d_duty:String? = null //수수료
    var fb = FirebaseDatabase.getInstance()
    val TAG:String = "Document"

    fun is_empty():Boolean{
        Log.v("Document", d_postnickname+": "+d_url+", "+d_title+", "+d_category.toString()+", "+d_market+", "+d_info +", "+ d_num.toString()+", "+
                d_price +", "+d_place +", "+d_duty)
        return (d_url == null || d_title == null || d_category == null || d_market == null || d_info == null || d_num == null ||
            d_price == null || d_place == null || d_duty == null)
    }
    fun regi_firebase(){
        val insert = fb.getReference("post").push()
        insert.child("nickname").setValue(d_postnickname)
        insert.child("uri").setValue(d_url)
        insert.child("title").setValue(d_title)
        insert.child("category").setValue(d_category)
        insert.child("on").setValue(d_on)
        insert.child("off").setValue(d_off)
        insert.child("link").setValue(d_market)
        insert.child("info").setValue(d_info)
        insert.child("num").setValue(d_num)
        insert.child("price").setValue(d_price)
        insert.child("place").setValue(d_place)
        insert.child("duty").setValue(d_duty)
        insert.child("time").setValue(d_now)
        uploadImageToFirebaseStorage()
    }
    fun uploadImageToFirebaseStorage(){
        if(d_url == null)return
        //val filename = UUID.randomUUID().toString()
        var selectedPhotoUri = Uri.parse(d_url)
        var ref= FirebaseStorage.getInstance().reference.child("images")
        var imagesRef = ref.child("${d_now}: ${d_postnickname}")
        imagesRef.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.v(TAG, "사진 업로드 성공")
                //d_url=it.uploadSessionUri.toString()
            }.addOnFailureListener {
                // Handle unsuccessful uploads
                Log.v(TAG, "사진 업로드 실패")
            }

    }

}