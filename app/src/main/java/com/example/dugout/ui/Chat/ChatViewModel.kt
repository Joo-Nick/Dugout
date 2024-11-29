package com.example.dugout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dugout.model.Chat
import com.example.dugout.repository.ChatRepository

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _acceptedChats = MutableLiveData<List<Chat>>(emptyList())
    val acceptedChats: LiveData<List<Chat>> get() = _acceptedChats

    private val _chatRequests = MutableLiveData<List<Chat>>(emptyList())
    val chatRequests: LiveData<List<Chat>> get() = _chatRequests


    init {
        // Firebase 데이터 관찰
        repository.observeAcceptedChats(_acceptedChats)
        repository.observeChatRequests(_chatRequests)
    }

    // 채팅 요청 수락
    fun acceptRequest(chat: Chat) {
        repository.moveToAcceptedChats(chat) // Firebase에서 AcceptedChats로 이동
    }

    // 채팅 요청 거절
    fun declineRequest(chat: Chat) {
        repository.deleteChatRequest(chat) // Firebase에서 ChatRequests에서 삭제
    }
}
