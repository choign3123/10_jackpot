package com.jackpot.jackpotfront.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jackpot.jackpotfront.retrofit.data.*
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

    // 게시물 생성
    @Multipart
    @POST("/posts/{userIdx}")
    fun postPost(
        @Path("userIdx") userIdx : Int?,
        @Part img: MultipartBody.Part,
        @Part ("content") content: RequestBody
        ): Call<PostPostResult>

    // 게시물 전체 조회
    @GET("/posts/{userIdx}")
    fun getAllPost(
        @Path("userIdx") userIdx : Int?,
        @Query("page") page: Int,
    ): Call<GetAllPostResult>

    // 게시물 삭제
    @DELETE("/posts/{userIdx}")
    fun deletePost(
        @Path("userIdx") userIdx : Int?,
        @Query("postIdx") postIdx: Int,
    ): Call<DeletePostResult>

    // 게시물 검색
    @GET("/posts/search/{userIdx}")
    fun searchPost(
        @Path("userIdx") userIdx: Int?,
        @Query("q") q: String,
        @Query("page") page: Int
    ): Call<GetAllPostResult>

    // 마이 게시물 조회
    @GET("/posts/my/{userIdx}")
    fun getMyPost(
        @Path("userIdx") userIdx: Int?,
        @Query("page") page: Int
    ): Call<GetMyPostResult>



    // 신고
//    @PATCH("/posts/notify/{postIdx}")
//    fun patchReport(
//        @Path("postIdx") postIdx: Int,
//    )


    // 싱글톤
    companion object {
        private const val BASE_URL = "https://test-domains.shop"

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