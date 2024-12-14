package com.example.dugout.model

data class Chat(
    var id: String = "", // Firebase 고유 키
    val name: String = "",
    var message: String = "", // val -> var
    var time: String = "",    // val -> var
    val profileImageRes: String = "",
    var accepted: Boolean = false
)
