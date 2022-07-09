package com.jackpot.jackpotfront.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding :ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, OneFragment())
            .commitAllowingStateLoss()
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.postFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, OneFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.clothesFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, TwoFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.myPageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ThreeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true

                }
            }
            false
        }
    }
}