package com.example.dugout.ui.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R
import com.example.dugout.model.Chat

class ChatAcceptedAdapter(
    private var acceptedChats: List<Chat>
) : RecyclerView.Adapter<ChatAcceptedAdapter.ChatAcceptedViewHolder>() {

    inner class ChatAcceptedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textChat: TextView = itemView.findViewById(R.id.textChat)
        val textTime: TextView = itemView.findViewById(R.id.textTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAcceptedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_accepted_chat, parent, false)
        return ChatAcceptedViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatAcceptedViewHolder, position: Int) {
        val chat = acceptedChats[position]
        holder.textName.text = chat.name
        holder.textChat.text = chat.message
        holder.textTime.text = chat.time
        holder.profileImage.setImageResource(chat.profileImageResId)
    }

    override fun getItemCount(): Int = acceptedChats.size

    fun updateData(newChats: List<Chat>) {
        acceptedChats = newChats
        notifyDataSetChanged()
    }
}
