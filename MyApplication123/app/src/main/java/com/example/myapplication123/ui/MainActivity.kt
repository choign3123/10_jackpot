package com.example.myapplication123.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication123.R
import com.example.myapplication123.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBottomNavigation()
    }
    
}