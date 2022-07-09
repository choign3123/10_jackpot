package com.jackpot.jackpotfront.ui

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.jackpot.jackpotfront.databinding.ActivityLoginBinding
import com.jackpot.jackpotfront.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}
    private var isHearting: Boolean = false

    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    fun onClickButton(view: View) {
        if(!isHearting){ //기본이 false이므로 false가 아닐때 실행한다.
            //애니메이션의 커스텀
            //0f가 0퍼센트, 1F가 100퍼센트
            //ofFloat(시작지점, 종료지점).setDuration(지속시간)
            // Custom animation speed or duration.
            val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(500)
            animator.addUpdateListener {
                binding.favButton.progress = it.animatedValue as Float
            }
            animator.start()
            isHearting = true // 그리고 트루로 바꾼다.
            Log.d(TAG, "MainActivity - onClickButton() called / 좋아요 버튼이 클릭됨")
        }else{ //트루일때가 실행된다.
            val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(500)
            animator.addUpdateListener {
                binding.favButton.progress = it.animatedValue as Float
            }
            animator.start()
            isHearting = false // 다시 false로 된다.
            Log.d(TAG, "MainActivity - onClickButton() called / 좋아요 버튼이 꺼짐")
        }
    }
}