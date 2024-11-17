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

    fun setMatchingItem(item: MatchingItem) {
        loadUserData(item.userId)
        loadReviews(item.userId)
    }

    private fun loadUserData(userId: String) {
        viewModelScope.launch {
            try {
                val userProfile = repository.getUserProfile(userId)
                _userData.value = userProfile
            } catch (e: Exception) {
                // 에러 처리
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
