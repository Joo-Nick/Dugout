package com.example.dugout.ui.Matching

data class Event(
    val user_id: String = "",
    val date: String = "",
    val stadium: String = "",
    val message: String = "",
    val ticket: Boolean = false
)
