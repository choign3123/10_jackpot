package com.jackpot.jackpotfront.retrofit.data

data class LoginInfo(
    val id: String?,
    val password: String?
)

data class LoginResult(
    val isSuccess: Boolean,
    val message: String,
    val result: LoginObject
)

data class LoginObject(
    val userIdx: Int
)
