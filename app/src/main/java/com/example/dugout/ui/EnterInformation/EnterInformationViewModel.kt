package com.example.dugout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dugout.model.EnterInformation
import com.example.dugout.repository.EnterInformationRepository
import kotlinx.coroutines.launch

class EnterInformationViewModel : ViewModel() {
    private val repository = EnterInformationRepository()

    private val _enterInfoList = MutableLiveData<List<EnterInformation>>()
    val enterInfoList: LiveData<List<EnterInformation>> get() = _enterInfoList

    fun fetchEnterInformation() {
        viewModelScope.launch {
            _enterInfoList.value = repository.getEnterInformation()
        }
    }

    fun saveEnterInformation(info: EnterInformation) {
        viewModelScope.launch {
            repository.saveEnterInformation(info)
        }
    }
}
