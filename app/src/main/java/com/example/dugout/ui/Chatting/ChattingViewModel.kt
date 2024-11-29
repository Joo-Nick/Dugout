package com.example.dugout.ui.Chatting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class ChattingViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<ChattingItem>>()
    val messages: LiveData<List<ChattingItem>> get() = _messages

    private lateinit var repository: ChattingRepository
    private lateinit var chatId: String
    private val currentUserId: String = "user123"

    fun initialize(chatId: String) {
        this.chatId = chatId
        repository = ChattingRepository(FirebaseDatabase.getInstance())
        loadMessages()
    }

    private fun loadMessages() {
        repository.getMessages(chatId).observeForever { messages ->
            val updatedMessages = messages.map { message ->
                message.copy(isSentByCurrentUser = message.userId == currentUserId)
            }
            _messages.value = updatedMessages
        }
    }

    fun sendMessage(message: String, userId: String) {
        val newMessage = ChattingItem(
            message = message,
            userId = userId,
            timestamp = System.currentTimeMillis(),
            isSentByCurrentUser = userId == currentUserId
        )
        repository.sendMessage(chatId, newMessage)
    }
}
