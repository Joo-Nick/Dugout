// MatchingProfileViewModel.kt
package com.example.dugout.ui.Matching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MatchingProfileViewModel : ViewModel() {

    private val repository = MatchingProfileRepository()

    private val _userData = MutableLiveData<UserProfile>()
    val userData: LiveData<UserProfile> get() = _userData

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    fun fetchUser(userId: String) {
        loadUserData(userId)
        loadReviews(userId)
    }

    private fun loadUserData(userId: String) {
        viewModelScope.launch {
            try {
                val userProfile = repository.getUserProfile(userId)
                _userData.value = userProfile
            } catch (e: Exception) {
                _userData.value = UserProfile(
                    name = "알 수 없음",
                    team = "알 수 없음",
                    rating = 0.0,
                    profile_message = "정보가 없습니다.",
                    profileImageRes = "default_profile",
                    winRate = 0,
                    reviewCount = 0
                )
            }
        }
    }

    private fun loadReviews(userId: String) {
        viewModelScope.launch {
            try {
                val userReviews = repository.getUserReviews(userId)
                _reviews.value = userReviews
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}
