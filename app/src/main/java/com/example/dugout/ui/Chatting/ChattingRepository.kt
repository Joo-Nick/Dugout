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

    // 특정 채팅 ID에 해당하는 메시지 가져오기
    fun getMessages(chatId: String): LiveData<List<ChattingItem>> {
        val liveData = MutableLiveData<List<ChattingItem>>()
        val messagesRef: DatabaseReference = database.getReference("messages").child(chatId)

        messagesRef.orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<ChattingItem>()

                    for (childSnapshot in snapshot.children) {
                        val message = childSnapshot.getValue(ChattingItem::class.java)
                        if (message != null) {
                            messages.add(message)
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

    // 새 메시지 저장
    fun sendMessage(chatId: String, message: ChattingItem) {
        val messagesRef: DatabaseReference = database.getReference("messages").child(chatId)
        val newMessageRef = messagesRef.push()

        newMessageRef.setValue(message)
            .addOnSuccessListener {
                Log.d("ChattingRepository", "Message sent successfully!")
            }
            .addOnFailureListener { e ->
                Log.w("ChattingRepository", "Error sending message", e)
            }
    }
}
