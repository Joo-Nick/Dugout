package com.example.dugout.ui.Chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Chat Fragment"
    }
    val text: LiveData<String> = _text
}