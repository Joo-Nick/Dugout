package com.example.dugout.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R
import com.example.dugout.model.Chat

class ChatAcceptedAdapter(
    private var chatList: List<Chat>,
    private val onChatClick: (Chat) -> Unit // 클릭 이벤트 콜백
) : RecyclerView.Adapter<ChatAcceptedAdapter.ChatViewHolder>() {

    // ViewHolder 클래스 정의
    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.profileImage)
        val name: TextView = view.findViewById(R.id.textName)
        val message: TextView = view.findViewById(R.id.textChat)
        val time: TextView = view.findViewById(R.id.textTime)
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_accepted_chat, parent, false)
        return ChatViewHolder(view)
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]

        // 채팅 데이터 바인딩
        holder.name.text = chat.name
        holder.message.text = chat.message
        holder.time.text = chat.time

        // 프로필 이미지 설정
        holder.profileImage.setImageResource(
            holder.itemView.context.resources.getIdentifier(
                chat.profileImageRes,
                "drawable",
                holder.itemView.context.packageName
            )
        )

        // 클릭 이벤트 설정
        holder.itemView.setOnClickListener {
            onChatClick(chat) // 클릭된 채팅 객체를 콜백 함수로 전달
        }
    }

    // 아이템 개수 반환
    override fun getItemCount(): Int = chatList.size

    // 데이터 업데이트 메서드
    fun updateData(newChatList: List<Chat>) {
        chatList = newChatList
        notifyDataSetChanged()
    }

}
