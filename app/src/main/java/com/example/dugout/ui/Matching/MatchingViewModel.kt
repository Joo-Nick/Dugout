package com.example.dugout.ui.Matching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MatchingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Matching Fragment"
    }
    val text: LiveData<String> = _text
}