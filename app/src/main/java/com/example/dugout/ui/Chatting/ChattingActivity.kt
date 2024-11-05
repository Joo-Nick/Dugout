package com.example.dugout.ui.Chatting

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R

class ChattingActivity : AppCompatActivity() {
    private lateinit var chattingRecyclerView: RecyclerView
    private lateinit var chattingAdapter: ChattingAdapter
    private lateinit var messageList: ArrayList<String>
    private lateinit var messageInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        chattingRecyclerView = findViewById(R.id.rcc_chatting)
        messageInput = findViewById(R.id.edt_input)
        val sendButton = findViewById<Button>(R.id.btn_send)

        messageList = ArrayList()
        chattingAdapter = ChattingAdapter(messageList)
        chattingRecyclerView.layoutManager = LinearLayoutManager(this)
        chattingRecyclerView.adapter = chattingAdapter

        sendButton.setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                messageList.add(message)
                chattingAdapter.notifyItemInserted(messageList.size - 1)
                chattingRecyclerView.scrollToPosition(messageList.size - 1)
                messageInput.setText("")
                Log.d("ChattingActivity", "Current messages: $messageList")
            }
        }
    }
}