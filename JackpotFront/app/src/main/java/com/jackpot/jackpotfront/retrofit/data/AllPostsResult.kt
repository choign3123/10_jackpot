package com.jackpot.jackpotfront.retrofit.data

// 전체 게시글 출력 시 데이터 클래스
data class AllPostsResult(
    val isSuccess:	Boolean,
    val code:	Int,
    val message:	String,

    val result : ArrayList<AllPostsObject>
)

data class  AllPostsObject(
    val postIdx:	Int,
    val imgUrl:	String,
    val contents: String
)
