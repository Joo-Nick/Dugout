package com.example.dugout.ui.Chatting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dugout.ui.Matching.Event
import com.example.dugout.ui.Matching.MatchingItem
import com.example.dugout.ui.Matching.User
import com.example.dugout.ui.Matching.UserProfile
import com.example.dugout.ui.Profile.ProfileItem
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class ChattingRepository(private val database: FirebaseDatabase) {
    suspend fun getChatUserDetails(chatId: String): ChatUser? {
        return try {
            val snapshot = database.getReference("Chat")
                .child("AcceptedChats")
                .child(chatId)
                .get()
                .await()
            snapshot.getValue(ChatUser::class.java) // ChatUser 객체로 매핑
        } catch (e: Exception) {
            Log.e("ChattingRepository", "Failed to fetch chat user details", e)
            null
        }
    }
    // 메시지 목록을 가져오는 함수
    fun getMessages(chatId: String): LiveData<List<ChattingItem>> {
        val liveData = MutableLiveData<List<ChattingItem>>()

        val messagesRef: DatabaseReference = database.getReference("Chat").child("AcceptedChats").child(chatId).child("messages")

        messagesRef.orderByChild("time")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<ChattingItem>()

                    for (childSnapshot in snapshot.children) {
                        val time = childSnapshot.child("time").getValue(Long::class.java)
                        val userId = childSnapshot.child("userId").getValue(String::class.java)
                        val message = childSnapshot.child("message").getValue(String::class.java)

                        if (time != null && userId != null && message != null) {
                            messages.add(ChattingItem(
                                time = time,
                                userId = userId,
                                message = message
                            ))
                        }
                    }

                    liveData.value = messages
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("ChattingRepository", "Error fetching messages", error.toException())
                }
            })

        return liveData
    }

    // 새 메시지를 Realtime Database에 저장하는 함수
    fun sendMessage(chatId: String, message: ChattingItem) {
        val messagesRef: DatabaseReference = database.getReference("Chat").child("AcceptedChats").child(chatId).child("messages")
        val newMessageRef = messagesRef.push()

        val messageMap = mapOf(
            "time" to System.currentTimeMillis(),
            "userId" to message.userId,
            "message" to message.message
        )

        newMessageRef.setValue(messageMap)
            .addOnSuccessListener {
                Log.d("ChattingRepository", "Message sent successfully!")
            }
            .addOnFailureListener { e ->
                Log.w("ChattingRepository", "Error sending message", e)
            }
    }
}