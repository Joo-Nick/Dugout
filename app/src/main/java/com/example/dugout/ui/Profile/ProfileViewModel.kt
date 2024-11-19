package com.example.dugout.ui.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _profile = MutableLiveData<ProfileItem?>() // null 가능하도록 설정
    val profile: LiveData<ProfileItem?> get() = _profile

    private val repository = ProfileRepository()

    private val _profileUpdateStatus = MutableLiveData<String>()
    val profileUpdateStatus: LiveData<String> get() = _profileUpdateStatus

    // Firebase에서 프로필 정보를 가져오는 함수 (user1만)
    fun fetchProfile() {
        repository.getProfile { profile ->
            _profile.value = profile // 단일 프로필을 LiveData에 설정
        }
    }

    fun sendProfile(name: String, profileImageRes: String, profile_message: String, team: String) {
        val newProfile = ProfileItem(
            name = name,
            profileImageRes = profileImageRes,
            profile_message = profile_message,
            team = team,
            gender = "",
            rating = ""
        )
        repository.sendProfile(newProfile)
    }
}
