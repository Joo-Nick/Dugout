package com.example.dugout.repository

import androidx.lifecycle.MutableLiveData
import com.example.dugout.model.Chat
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class ChatRepository {
    private val database = FirebaseDatabase.getInstance()
    private val acceptedChatsRef = database.getReference("Chat/AcceptedChats")
    private val chatRequestsRef = database.getReference("Chat/ChatRequests")

    fun observeAcceptedChats(acceptedChats: MutableLiveData<List<Chat>>) {
        acceptedChatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val chats = snapshot.children.mapNotNull { chatSnapshot ->
                        val chat = chatSnapshot.getValue(Chat::class.java)
                        chat?.let { chatData ->
                            // messages 노드에서 가장 최신 메시지 가져오기
                            val latestMessage = chatSnapshot.child("messages").children.maxByOrNull {
                                it.child("time").getValue(Long::class.java) ?: 0L
                            }
                            if (latestMessage != null) {
                                chatData.message = latestMessage.child("message").getValue(String::class.java) ?: ""
                                chatData.time = formatTime(latestMessage.child("time").getValue(Long::class.java) ?: 0L)
                            }
                            chatData
                        }
                    }
                    acceptedChats.postValue(chats)
                } else {
                    acceptedChats.postValue(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // No operation
            }
        })
    }

    fun observeChatRequests(chatRequests: MutableLiveData<List<Chat>>) {
        chatRequestsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val requests = snapshot.children.mapNotNull { chatSnapshot ->
                        val chat = chatSnapshot.getValue(Chat::class.java)
                        chat?.let { chatData ->
                            // messages 노드에서 가장 최신 메시지 가져오기
                            val latestMessage = chatSnapshot.child("messages").children.maxByOrNull {
                                it.child("time").getValue(Long::class.java) ?: 0L
                            }
                            if (latestMessage != null) {
                                chatData.message = latestMessage.child("message").getValue(String::class.java) ?: ""
                                chatData.time = formatTime(latestMessage.child("time").getValue(Long::class.java) ?: 0L)
                            }
                            chatData
                        }
                    }
                    chatRequests.postValue(requests)
                } else {
                    chatRequests.postValue(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // No operation
            }
        })
    }

    private fun formatTime(time: Long): String {
        return if (time > 0) {
            val date = Date(time)
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            formatter.format(date)
        } else {
            "N/A"
        }
    }

    fun moveToAcceptedChats(chat: Chat) {
        val newChat = chat.copy(accepted = true)
        val newKey = acceptedChatsRef.push().key
        if (newKey != null) {
            acceptedChatsRef.child(newKey).setValue(newChat)
            chatRequestsRef.child(chat.id).removeValue()
        }
    }

    fun deleteChatRequest(chat: Chat) {
        chatRequestsRef.child(chat.id).removeValue()
    }
}
