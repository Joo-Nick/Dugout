package com.example.dugout.ui.Chatting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class ChattingViewModel : ViewModel() {

    // LiveData로 메시지 목록을 저장
    private val _messages = MutableLiveData<List<ChattingItem>>()
    val messages: LiveData<List<ChattingItem>> get() = _messages

    private val _chatUserDetails = MutableLiveData<ChatUser>()
    val chatUserDetails: LiveData<ChatUser> get() = _chatUserDetails

    private lateinit var repository: ChattingRepository
    private lateinit var chatId: String


    // 현재 사용자 ID (예시로 user123 사용)
    private val currentUserId: String = "user123"
    // Repository 주입
    fun initialize(chatId: String) {
        this.chatId = chatId
        repository = ChattingRepository(FirebaseDatabase.getInstance())
    }
    fun loadChatUserDetails() {
        viewModelScope.launch {
            val chatUser = repository.getChatUserDetails(chatId)
            _chatUserDetails.value = chatUser ?: ChatUser(name = "알 수 없음", profileImageRes = "default_profile_url")
        }
    }

    // Firestore에서 메시지를 로드하고, 현재 사용자에 맞게 메시지를 업데이트
    fun loadMessages() {
        repository.getMessages(chatId).observeForever { messages ->
            // messages 변환 및 LiveData 업데이트
            _messages.value = messages.map { message ->
                message.copy(isSentByCurrentUser = message.userId == currentUserId)
            }
        }
    }


    fun sendMessage(message: String, userId: String) {
        val newMessage = ChattingItem(message = message, userId = userId, time = System.currentTimeMillis(), isSentByCurrentUser = userId == currentUserId)
        repository.sendMessage(chatId, newMessage)
    }
}
