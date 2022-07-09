package com.jackpot.jackpotfront.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ActivityAddPostsBinding
import com.jackpot.jackpotfront.databinding.ActivityMainBinding

class AddPostsActivity : AppCompatActivity() {
    val binding by lazy { ActivityAddPostsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}