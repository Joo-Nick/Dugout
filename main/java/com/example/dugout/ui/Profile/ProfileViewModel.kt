package com.example.dugout.ui.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    fun onDateSelected(year: Int, month: Int, day: Int) {
        _selectedDate.value = "$year-${month + 1}-$day"
    }

    fun submitProfile(stadium: String, hasTicket: Boolean?, gender: String, date: Long) {
        // 프로필 정보 처리 및 저장 로직 구현
        // 예: 로그 출력 또는 데이터베이스에 저장
        println("프로필 등록: 경기장 = $stadium, 티켓 = $hasTicket, 성별 = $gender, 날짜 = $date")
    }
}
