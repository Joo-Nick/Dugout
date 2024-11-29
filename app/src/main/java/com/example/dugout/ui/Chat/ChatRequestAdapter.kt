package com.example.dugout.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R
import com.example.dugout.model.Chat

class ChatRequestAdapter(
    private var chatRequests: List<Chat>,
    private val onRequestAction: (Chat, Boolean) -> Unit // 수락/거절 콜백
) : RecyclerView.Adapter<ChatRequestAdapter.ChatRequestViewHolder>() {

    inner class ChatRequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.profileImageRequest)
        val name: TextView = view.findViewById(R.id.textNameRequest)
        val acceptButton: Button = view.findViewById(R.id.acceptButton)
        val declineButton: Button = view.findViewById(R.id.declineButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_request, parent, false)
        return ChatRequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatRequestViewHolder, position: Int) {
        val chatRequest = chatRequests[position]

        holder.name.text = chatRequest.name
        holder.profileImage.setImageResource(
            holder.itemView.context.resources.getIdentifier(
                chatRequest.profileImageRes,
                "drawable",
                holder.itemView.context.packageName
            )
        )

        // 수락 버튼 클릭 이벤트
        holder.acceptButton.setOnClickListener { onRequestAction(chatRequest, true) }

        // 거절 버튼 클릭 이벤트
        holder.declineButton.setOnClickListener { onRequestAction(chatRequest, false) }
    }

    override fun getItemCount(): Int = chatRequests.size

    fun updateData(newChatRequests: List<Chat>) {
        Log.d("ChatRequestAdapter", "Updating data with $newChatRequests")
        chatRequests = newChatRequests
        notifyDataSetChanged()
    }
}
