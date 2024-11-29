package com.example.dugout.model

data class Chat(
    var id: String = "", // Firebase 고유 키
    val name: String = "",
    val message: String = "",
    val time: String = "",
    val profileImageRes: String = "",
    var accepted: Boolean = false
)