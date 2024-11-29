package com.example.dugout.ui.Chatting

import com.google.firebase.Timestamp

data class ChattingItem(
    val timestamp: Long,
    val userId: String,
    val message: String,
    var isSentByCurrentUser: Boolean = false
)