package com.example.dugout.ui.Matching

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MatchingViewModel : ViewModel() {
    val repository = MatchingRepository()

    private val _matchingItems = MutableLiveData<List<MatchingItem>>()
    val matchingItems: LiveData<List<MatchingItem>> get() = _matchingItems

    // 평점 높은 순으로 정렬된 데이터 로드
    fun loadItemsByRatingDesc() {
        viewModelScope.launch {
            try {
                val items = repository.getItemsSortedByRatingDesc()
                _matchingItems.value = items
            } catch (e: Exception) {
                // 예외 처리 로그 추가
                Log.e("MatchingViewModel", "Failed to load items by rating desc: ${e.message}")
            }
        }
    }

    fun loadAllItems() {
        viewModelScope.launch {
            try {
                val items = repository.getAllMatchingItems()
                _matchingItems.value = items
            } catch (e: Exception) {
                Log.e("MatchingViewModel", "Failed to load all items: ${e.message}")
            }
        }
    }

    // 평점 낮은 순으로 정렬된 데이터 로드
    fun loadItemsByRatingAsc() {
        viewModelScope.launch {
            try {
                val items = repository.getItemsSortedByRatingAsc()
                _matchingItems.value = items
            } catch (e: Exception) {
                Log.e("MatchingViewModel", "Failed to load items by rating asc: ${e.message}")
            }
        }
    }

    // 성별에 따른 데이터 필터링 로드
    fun loadItemsByGender(gender: String?) {
        viewModelScope.launch {
            try {
                val items = repository.getItemsByGender(gender)
                _matchingItems.value = items
            } catch (e: Exception) {
                Log.e("MatchingViewModel", "Failed to load items by gender: ${e.message}")
            }
        }
    }

    // 티켓 유무에 따른 데이터 필터링 로드
    fun loadItemsByTicketAvailability(hasTicket: Boolean?) {
        viewModelScope.launch {
            try {
                val items = repository.getItemsByTicketAvailability(hasTicket)
                _matchingItems.value = items
            } catch (e: Exception) {
                Log.e("MatchingViewModel", "Failed to load items by ticket availability: ${e.message}")
            }
        }
    }

    // 날짜 오래된 순으로 정렬된 데이터 로드
    fun loadItemsByDateAsc() {
        viewModelScope.launch {
            try {
                val items = repository.getItemsSortedByDateAsc()
                _matchingItems.value = items
            } catch (e: Exception) {
                Log.e("MatchingViewModel", "Failed to load items by date asc: ${e.message}")
            }
        }
    }

    // 날짜 최신 순으로 정렬된 데이터 로드
    fun loadItemsByDateDesc() {
        viewModelScope.launch {
            try {
                val items = repository.getItemsSortedByDateDesc()
                _matchingItems.value = items
            } catch (e: Exception) {
                Log.e("MatchingViewModel", "Failed to load items by date desc: ${e.message}")
            }
        }
    }
}