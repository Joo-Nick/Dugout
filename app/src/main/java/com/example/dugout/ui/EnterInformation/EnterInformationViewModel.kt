package com.example.dugout.ui.EnterInformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnterInformationViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    fun onDateSelected(year: Int, month: Int, day: Int) {
        _selectedDate.value = "$year-${month + 1}-$day"
    }

    fun submitInformation(stadium: String, hasTicket: Boolean?, gender: String, date: Long) {
        println("정보 등록: 경기장 = $stadium, 티켓 = $hasTicket, 성별 = $gender, 날짜 = $date")//
    }
}
