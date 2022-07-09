package com.jackpot.jackpotfront.retrofit.data

data class SearchPostResult(
    val isSuccess: Boolean,
    val message: String,
    val result: SearchPostObject
)

data class SearchPostObject(
    val postIdx: Int,
    val imgUrl: String,
    val content: String,
    // 이모지
)
