package com.example.dugout.ui.Chatting

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R
import com.google.firebase.database.FirebaseDatabase

class ChattingActivity : AppCompatActivity() {

    private lateinit var chattingViewModel: ChattingViewModel
    private lateinit var repository: ChattingRepository
    private lateinit var chattingAdapter: ChattingAdapter
    private lateinit var chattingRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button

    // 사용자 ID (예시로 직접 입력된 값)
    private val userId = "user123"  // 실제 사용자의 ID를 Firebase Auth 등에서 가져오세요.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        // Firebase 인스턴스 초기화
        val database = FirebaseDatabase.getInstance()
        repository = ChattingRepository(database)

        // ViewModel 초기화
        chattingViewModel = ViewModelProvider(this).get(ChattingViewModel::class.java)
        chattingViewModel.initialize(repository)

        // RecyclerView 초기화
        chattingRecyclerView = findViewById(R.id.rcc_chatting)
        chattingRecyclerView.layoutManager = LinearLayoutManager(this)
        chattingAdapter = ChattingAdapter(emptyList())
        chattingRecyclerView.adapter = chattingAdapter

        // 메시지 입력창과 버튼 초기화
        messageInput = findViewById(R.id.edt_input)
        sendButton = findViewById(R.id.btn_send)

        // 메시지 목록 관찰
        chattingViewModel.messages.observe(this) { messages ->
            chattingAdapter.setMessages(messages)
        }

        // 보내기 버튼 클릭 시 메시지 전송
        sendButton.setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                chattingViewModel.sendMessage(message, userId)
                messageInput.setText("")  // 메시지 입력칸 초기화
            }
        }
    }
}

