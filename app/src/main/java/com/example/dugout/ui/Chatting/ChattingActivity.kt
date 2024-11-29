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
    private lateinit var chattingAdapter: ChattingAdapter
    private lateinit var chattingRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button

    private lateinit var chatId: String // 전달받은 채팅 ID
    private val userId = "user123" // 사용자 ID (예: Firebase Auth에서 가져옴)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        // Intent로 전달받은 채팅 ID와 이름 가져오기
        chatId = intent.getStringExtra("chatId") ?: ""
        val chatName = intent.getStringExtra("chatName") ?: "채팅"

        // 액션바 제목 설정
        supportActionBar?.title = chatName

        // ViewModel 초기화
        chattingViewModel = ViewModelProvider(this).get(ChattingViewModel::class.java)
        chattingViewModel.initialize(chatId)

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
            chattingRecyclerView.scrollToPosition(messages.size - 1) // 최신 메시지로 스크롤
        }

        // 보내기 버튼 클릭 시 메시지 전송
        sendButton.setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                chattingViewModel.sendMessage(message, userId)
                messageInput.setText("") // 입력칸 초기화
            }
        }
    }
}
