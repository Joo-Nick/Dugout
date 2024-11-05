package com.example.dugout.model

data class Chat(
    val name: String,
    val message: String,
    val time: String,
    val profileImageResId: Int // 프로필 이미지의 리소스 ID
)
