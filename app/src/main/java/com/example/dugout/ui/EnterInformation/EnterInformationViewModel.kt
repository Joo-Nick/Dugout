package com.example.dugout.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dugout.model.EnterInformation
import com.example.dugout.repository.EnterInformationRepository
import kotlinx.coroutines.launch

class EnterInformationViewModel : ViewModel() {
    private val repository = EnterInformationRepository()


    fun saveEnterInformation(info: EnterInformation) {
        viewModelScope.launch {
            repository.saveEnterInformation(info)
        }
    }
}
