package com.example.dugout.ui.Matching

data class UserProfile(
    val profileImageRes: String = "",
    val name: String = "",
    val team: String = "",
    val winRate: Int = 70,
    val rating: Double = 0.0,
    val reviewCount: Int = 2,
    val profile_message: String = ""
)