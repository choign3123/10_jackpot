package com.jackpot.jackpotfront.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBottomNavigation()

        // 모든 게시물 조회
        getAllPosts()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, PostFragment())
            .commitAllowingStateLoss()
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.postFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, PostFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.clothesFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.myPageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyPageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true

                }
            }
            false
        }

    }

    private fun getAllPosts() {
        // 레트로핏 통신
        val listManager = GridLayoutManager(this@MainActivity, 1)
        var listAdapter = ListAdapterGrid(this@MainActivity, userIdx, response.body()!!.result)

        var recyclerList = binding.gridView.apply {
            setHasFixedSize(true)
            layoutManager = listManager
            adapter = listAdapter
    }
}