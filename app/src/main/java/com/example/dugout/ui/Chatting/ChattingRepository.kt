package com.example.dugout.ui.Chatting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ChattingRepository(private val database: FirebaseDatabase) {

    // 메시지 목록을 가져오는 함수
    fun getMessages(): LiveData<List<ChattingItem>> {
        val liveData = MutableLiveData<List<ChattingItem>>()

        val messagesRef: DatabaseReference = database.getReference("messages")

        messagesRef.orderByChild("timestamp") // timestamp 기준으로 정렬
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<ChattingItem>()

                    for (childSnapshot in snapshot.children) {
                        val timestamp = childSnapshot.child("timestamp").getValue(Long::class.java)
                        val userId = childSnapshot.child("userId").getValue(String::class.java)
                        val message = childSnapshot.child("message").getValue(String::class.java)

                        if (timestamp != null && userId != null && message != null) {
                            messages.add(ChattingItem(
                                timestamp = timestamp,
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
    fun sendMessage(message: ChattingItem) {
        val messagesRef: DatabaseReference = database.getReference("messages")
        val newMessageRef = messagesRef.push()  // 고유한 ID로 새 메시지 추가

        // 메시지 객체를 Map으로 변환해서 저장
        val messageMap = mapOf(
            "timestamp" to System.currentTimeMillis(),
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





