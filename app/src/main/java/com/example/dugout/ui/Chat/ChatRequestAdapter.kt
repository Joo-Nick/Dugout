package com.example.dugout.ui.Chat

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
    private val onRequestAction: (Chat, Boolean) -> Unit
) : RecyclerView.Adapter<ChatRequestAdapter.ChatRequestViewHolder>() {

    inner class ChatRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profileImageRequest)
        val textName: TextView = itemView.findViewById(R.id.textNameRequest)
        val acceptButton: Button = itemView.findViewById(R.id.acceptButton)
        val declineButton: Button = itemView.findViewById(R.id.declineButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_request, parent, false)
        return ChatRequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatRequestViewHolder, position: Int) {
        val chatRequest = chatRequests[position]
        holder.textName.text = chatRequest.name
        holder.profileImage.setImageResource(chatRequest.profileImageResId)

        holder.acceptButton.setOnClickListener {
            onRequestAction(chatRequest, true)
        }
        holder.declineButton.setOnClickListener {
            onRequestAction(chatRequest, false)
        }
    }

    override fun getItemCount(): Int = chatRequests.size

    fun updateData(newChatRequests: List<Chat>) {
        chatRequests = newChatRequests
        notifyDataSetChanged()
    }
}
