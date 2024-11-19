package com.example.dugout.ui.Profile

data class ProfileItem(
    val gender: String,
    val name: String,
    val profileImageRes: String,
    val profile_message: String,  // 이름 일치 확인
    val rating: String,
    val team: String
)