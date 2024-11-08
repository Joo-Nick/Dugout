package com.example.dugout.ui.Matching

import java.time.LocalDate

data class MatchingItem(
    val profileImageRes: String = "",
    val name: String = "",
    val rating: Double = 0.0,
    val message: String = "",
    val gender: String = "",
    val ticket: Boolean = false,
    val date: String = ""
)
