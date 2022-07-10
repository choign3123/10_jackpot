package com.jackpot.jackpotfront.retrofit.data

data class GetAllPostResult(
    val isSuccess: Boolean,
    val message: String,
    val result: List<GetAllPostObject>
)

data class GetAllPostObject(
    val postIdx: Int,
    val imgUrl: String,
    val content: String,
    val userName: String,
    val checkEmoji: List<Boolean>
)
