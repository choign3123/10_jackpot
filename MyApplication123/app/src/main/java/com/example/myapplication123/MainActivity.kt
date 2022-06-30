package com.example.myapplication123

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun testGana(){
        android.util.Log.d("gana", "testGana: test")
    }

    fun testFun() {
        var abc:String?
    }

    fun testFun2(){
        
    }
}