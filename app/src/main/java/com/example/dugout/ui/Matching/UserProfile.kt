package com.example.dugout.ui.Matching

data class UserProfile(
    val name: String = "",
    val team: String = "",
    val rating: Double = 0.0,
    val profileMessage: String = "",
    val profileImageRes: String = "",
    val winRate: Int = 0,
    val reviewCount: Int = 0
)