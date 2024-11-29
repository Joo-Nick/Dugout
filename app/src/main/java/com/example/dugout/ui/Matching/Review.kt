package com.example.dugout.ui.Matching

data class Review(
    val to_user_id: String = "",
    val from_user_id: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val date: String = "",
    var fromUserName: String = "" // 작성자 이름 추가
)
