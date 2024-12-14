package com.example.dugout.ui.Chatting

data class ChattingItem(
    val time: Long = 0L,
    val userId: String = "",
    val message: String = "",
    var isSentByCurrentUser: Boolean = false // UI 반영용
)
