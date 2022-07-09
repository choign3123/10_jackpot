package com.jackpot.jackpotfront.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jackpot.jackpotfront.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}