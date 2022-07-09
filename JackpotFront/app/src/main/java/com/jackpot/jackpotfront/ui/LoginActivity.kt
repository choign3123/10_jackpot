package com.jackpot.jackpotfront.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.jackpot.jackpotfront.databinding.ActivityLoginBinding
import com.jackpot.jackpotfront.retrofit.RetrofitService
import com.jackpot.jackpotfront.retrofit.data.LoginInfo
import com.jackpot.jackpotfront.retrofit.data.LoginResult
import com.jackpot.jackpotfront.retrofit.data.UserIdxObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}
    val retro = RetrofitService.create()



    // 로그인 데이터 선언
    var id: String? = null
    var pw: String? = null
    var loginData: LoginInfo = LoginInfo(id,pw)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            id = binding.editText1.text.toString()
            pw = binding.editText2.text.toString()
            loginData = LoginInfo(id, pw)

            retro.postLogin(loginData).enqueue(object: Callback<LoginResult> {
                override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {

                    Log.d("MYTAG", id!!)
                    Log.d("MYTAG", pw!!)
                    Log.d("MYTAG", response.body().toString())

                    if(response.body()?.isSuccess == true) {

                        UserIdxObject.userIdx = response.body()?.result?.userIdx

                        val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(mainIntent)

                        Log.d("MYTAG", "SUCCESS")

                        Toast.makeText(this@LoginActivity, "SUCCESS!!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    Log.d("MYTAG",t.message.toString())
                    Log.d("MYTAG","FAIL")

                    Toast.makeText(this@LoginActivity, "FAIL!!!", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }
}