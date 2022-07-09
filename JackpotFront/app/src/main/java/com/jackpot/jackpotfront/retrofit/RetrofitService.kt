package com.jackpot.jackpotfront.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jackpot.jackpotfront.retrofit.data.LoginInfo
import com.jackpot.jackpotfront.retrofit.data.LoginResult
import com.jackpot.jackpotfront.retrofit.data.PostResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    // 함수 작성 부분
    // 로그인
    @POST("/users/login")
    fun postLogin(
        @Body jsonparams: LoginInfo
    ): Call<LoginResult>

    // 게시글 생성
    @Multipart
    @POST("/posts/{userIdx}")
    fun postPost(
        @Path("userIdx") userIdx : Int?,
        @Part img: MultipartBody.Part,
        @Part content: RequestBody
        ): Call<PostResult>

//    // 신고
//    @PATCH("/posts/notify/{postIdx}")
//    fun patchReport(
//        @Path("postIdx") postIdx: Int,
//    )


    // 싱글톤
    companion object {
        private const val BASE_URL = "?"

        fun create(): RetrofitService {

            val gson : Gson =   GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(ScalarsConverterFactory.create())

                .build()
                .create(RetrofitService::class.java)
        }
    }
}