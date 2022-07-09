package com.jackpot.jackpotfront.retrofit.data

data class GetAllPostResult(
    val isSuccess: Boolean,
    val message: String,
    val result: GetAllPostObject
)

data class GetAllPostObject(
    val postIdx: Int,
    val imgUrl: String,
    val content: String,
    // 이모지 추가
)
