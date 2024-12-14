package com.example.dugout.ui.Chatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R

class ChattingAdapter(private var messages: List<ChattingItem>) : RecyclerView.Adapter<ChattingAdapter.MessageViewHolder>() {

    // 새 데이터로 업데이트할 때 호출
    fun setMessages(newMessages: List<ChattingItem>) {
        this.messages = newMessages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = if (viewType == MESSAGE_SENT) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_chatting_sent, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_chatting_received, parent, false)
        }
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position] // 리스트에서 메시지 가져오기
        holder.bind(message)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSentByCurrentUser) {
            MESSAGE_SENT
        } else {
            MESSAGE_RECEIVED
        }
    }

    override fun getItemCount(): Int = messages.size // 메시지 리스트의 크기 반환

    class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.txt_message)

        fun bind(message: ChattingItem) {
            messageTextView.text = message.message
        }
    }

    companion object {
        const val MESSAGE_SENT = 1
        const val MESSAGE_RECEIVED = 2
    }
}


