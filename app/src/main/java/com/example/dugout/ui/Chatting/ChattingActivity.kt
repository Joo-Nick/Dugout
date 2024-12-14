package com.example.dugout.ui.Chatting

import VerticalItemSpacingDecoration
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dugout.R
import com.google.firebase.database.FirebaseDatabase

class ChattingActivity : AppCompatActivity() {
    private lateinit var btnBack : ImageButton
    private lateinit var chattingViewModel: ChattingViewModel
    private lateinit var chattingAdapter: ChattingAdapter
    private lateinit var chattingRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var NameTextView: TextView
    private lateinit var ProfileImageView: ImageView

    private lateinit var chatId: String // 전달받은 채팅 ID
    private val currentUserID = "user123"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        chatId = intent.getStringExtra("chatId") ?: run {
            finish()
            return
        }
        // ViewModel 초기화
        chattingViewModel = ViewModelProvider(this).get(ChattingViewModel::class.java)
        chattingViewModel.initialize(chatId)

        chattingViewModel.loadMessages()

        NameTextView = findViewById(R.id.tv_you)
        ProfileImageView = findViewById(R.id.iv_you)

        // RecyclerView 초기화
        chattingRecyclerView = findViewById(R.id.rcc_chatting)
        chattingRecyclerView.layoutManager = LinearLayoutManager(this)
        chattingAdapter = ChattingAdapter(emptyList())
        chattingRecyclerView.adapter = chattingAdapter
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.recycler_view_vertical_spacing)
        chattingRecyclerView.addItemDecoration(VerticalItemSpacingDecoration(verticalSpacing))


        // 메시지 입력창과 버튼 초기화
        messageInput = findViewById(R.id.edt_input)
        sendButton = findViewById(R.id.btn_send)

        btnBack = findViewById(R.id.btn_back)  // 레이아웃에서 정의한 뒤로가기 버튼 찾기
        btnBack.setOnClickListener {
            onBackPressed()  // 뒤로 가기 기능
        }

        chattingViewModel.loadChatUserDetails()

        chattingViewModel.chatUserDetails.observe(this) { chatUser ->
            NameTextView.text = chatUser.name
            val imageInformation = chatUser.profileImageRes
            if (imageInformation.startsWith("http")) {
                // URL로 처리
                Glide.with(this)
                    .load(imageInformation)  // Firebase에서 가져온 이미지 URL
                    .into(ProfileImageView)
            } else {
                // drawable 리소스 이름일 경우 처리
                val resourceId = resources.getIdentifier(imageInformation, "drawable", packageName)
                if (resourceId != 0) {
                    Glide.with(this)
                        .load(resourceId)  // drawable 리소스에서 로드
                        .into(ProfileImageView)
                }
            }

            // 메시지 목록 관찰
            chattingViewModel.messages.observe(this) { messages ->
                chattingAdapter.setMessages(messages)
                chattingRecyclerView.scrollToPosition(messages.size - 1) // 최신 메시지로 스크롤
            }


            // 보내기 버튼 클릭 시 메시지 전송
            sendButton.setOnClickListener {
                val message = messageInput.text.toString().trim()
                if (message.isNotEmpty()) {
                    chattingViewModel.sendMessage(message, currentUserID)
                    messageInput.setText("") // 입력칸 초기화
                }
            }
        }
    }
}