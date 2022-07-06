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

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, FragmentOne())
            .commitAllowingStateLoss()
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.postFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentOne())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.clothesFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentTwo())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.myPageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentThree())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true

                }
            }
            false
        }
    }
}