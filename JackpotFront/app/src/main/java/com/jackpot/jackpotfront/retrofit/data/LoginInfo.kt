package com.jackpot.jackpotfront.retrofit.data

data class LoginInfo(
    val id: String?,
    val pw: String?
)

data class LoginResult(
    val isSuccess: Boolean,
    val result: LoginObject
)

data class LoginObject(
    val userIdx: Int
)
