package com.example.dugout.ui.Chatting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChattingViewModel : ViewModel() {

    // LiveData로 메시지 목록을 저장
    private val _messages = MutableLiveData<List<ChattingItem>>()
    val messages: LiveData<List<ChattingItem>> get() = _messages

    private lateinit var repository: ChattingRepository

    // 현재 사용자 ID (예시로 user123 사용)
    private val currentUserId: String = "user123"

    // Repository 주입
    fun initialize(repository: ChattingRepository) {
        this.repository = repository
        loadMessages() // 메시지 로드
    }

    // Firestore에서 메시지를 로드하고, 현재 사용자에 맞게 메시지를 업데이트
    private fun loadMessages() {
        // LiveData를 observe()하면서 Firestore에서 가져온 메시지를 업데이트
        repository.getMessages().observeForever { messages ->
            val updatedMessages = messages.map { message ->
                // 메시지가 현재 사용자에 의해 보내졌는지 확인
                message.isSentByCurrentUser = message.userId == currentUserId
                message
            }
            _messages.value = updatedMessages  // 업데이트된 메시지를 LiveData에 저장
        }
    }

    fun sendMessage(message: String, userId: String) {
        val newMessage = ChattingItem(message = message, userId = userId, timestamp = System.currentTimeMillis(), isSentByCurrentUser = userId == currentUserId)
        repository.sendMessage(newMessage)
    }
}



