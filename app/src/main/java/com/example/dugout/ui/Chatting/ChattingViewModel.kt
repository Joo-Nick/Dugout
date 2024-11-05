package com.example.dugout.ui.Chatting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChattingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Chatting Fragment"
    }
    val text: LiveData<String> = _text
}