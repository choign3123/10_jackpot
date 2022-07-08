package com.example.testmultipart

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface RetrofitService {

    // 이미지 업로드
    @Multipart
    @POST("/test/upload")
    fun uploadIMG(
        @Part file: MultipartBody.Part
    ): Call<String>

    @GET("/test/display/{imgName}")
    fun getIMG(
        @Path("imgName") imgName: String?
    ): Call<ResponseBody>

    // 테스트
    @GET("/test/log")
    fun testLog(
    ): Call<LogResult>

    // 싱글톤
    companion object {
        private const val BASE_URL = "https://hana-umc.shop"

        fun create(): RetrofitService {

            val gson : Gson =   GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}