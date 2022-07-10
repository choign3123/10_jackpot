package com.jackpot.jackpotfront.retrofit.data

data class GetMyPostResult(
    val isSuccess: Boolean,
    val message: String,
    val result: GetMyPostObject
)

data class GetMyPostObject(
    val id: String,
    val numOfPost: Int,
    val numOfPoint: Int,
    val posts: List<PostsObject>
)

data class PostsObject (
    val postIdx: Int,
    val imgUrl: String,
    val content: String,
    val userName: String,
    val checkEmoji: List<Boolean>
        )
