package com.example.dugout.ui.Chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dugout.R
import com.example.dugout.model.Chat

class ChatViewModel : ViewModel() {

    private val _acceptedChats = MutableLiveData<List<Chat>>()
    val acceptedChats: LiveData<List<Chat>> get() = _acceptedChats

    private val _chatRequests = MutableLiveData<List<Chat>>()
    val chatRequests: LiveData<List<Chat>> get() = _chatRequests

    init {
        // 초기 데이터 설정 (예시)
        _chatRequests.value = listOf(
            Chat("김정원", "수락 대기 중", "", R.drawable.kimjungwon),
            Chat("박담비", "수락 대기 중", "", R.drawable.dambi),
            Chat("구자욱", "수락 대기 중", "", R.drawable.koojawook) //
        )
    }

    fun getChatRequests(): List<Chat> {
        return _chatRequests.value ?: emptyList()
    }

    fun acceptRequest(request: Chat) {
        val currentAcceptedChats = _acceptedChats.value?.toMutableList() ?: mutableListOf()
        currentAcceptedChats.add(request)
        _acceptedChats.value = currentAcceptedChats
    }

    fun declineRequest(request: Chat) {
        val currentRequests = _chatRequests.value?.toMutableList() ?: mutableListOf()
        currentRequests.remove(request)
        _chatRequests.value = currentRequests
    }
}
