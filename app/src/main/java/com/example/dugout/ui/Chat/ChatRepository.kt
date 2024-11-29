package com.example.dugout.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dugout.model.Chat
import com.google.firebase.database.*

class ChatRepository {
    private val database = FirebaseDatabase.getInstance()
    private val acceptedChatsRef = database.getReference("Chat/AcceptedChats")
    private val chatRequestsRef = database.getReference("Chat/ChatRequests")


    fun observeAcceptedChats(acceptedChats: MutableLiveData<List<Chat>>) {
        acceptedChatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val chats = snapshot.children.mapNotNull { it.getValue(Chat::class.java) }
                    acceptedChats.postValue(chats)
                    Log.d("FirebaseData", "AcceptedChats: $chats")
                } else {
                    acceptedChats.postValue(emptyList())
                    Log.d("FirebaseData", "No data found in AcceptedChats")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Error fetching AcceptedChats: ${error.message}")
            }
        })
    }

    fun observeChatRequests(chatRequests: MutableLiveData<List<Chat>>) {
        chatRequestsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val requests = snapshot.children.mapNotNull { it.getValue(Chat::class.java) }
                    chatRequests.postValue(requests)
                    Log.d("FirebaseData", "ChatRequests: $requests")
                } else {
                    chatRequests.postValue(emptyList())
                    Log.d("FirebaseData", "No data found in ChatRequests")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Error fetching ChatRequests: ${error.message}")
            }
        })
    }


    fun moveToAcceptedChats(chat: Chat) {
        val newChat = chat.copy(accepted = true)
        val newKey = acceptedChatsRef.push().key
        if (newKey != null) {
            acceptedChatsRef.child(newKey).setValue(newChat)
                .addOnSuccessListener {
                    chatRequestsRef.child(chat.id).removeValue()
                }
                .addOnFailureListener {
                    Log.e("ChatRepository", "Failed to move chat to AcceptedChats")
                }
        }
    }

    fun deleteChatRequest(chat: Chat) {
        chatRequestsRef.child(chat.id).removeValue()
            .addOnFailureListener {
                Log.e("ChatRepository", "Failed to delete chat request")
            }
    }
}

