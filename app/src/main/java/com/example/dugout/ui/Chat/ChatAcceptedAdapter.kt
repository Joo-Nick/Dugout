package com.example.dugout.ui.adapter

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
    private val onChatClick: (Chat) -> Unit
) : RecyclerView.Adapter<ChatAcceptedAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.profileImage)
        val name: TextView = view.findViewById(R.id.textName)
        val message: TextView = view.findViewById(R.id.textChat)
        val time: TextView = view.findViewById(R.id.textTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_accepted_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.name.text = chat.name
        holder.message.text = chat.message
        holder.time.text = chat.time

        holder.profileImage.setImageResource(
            holder.itemView.context.resources.getIdentifier(
                chat.profileImageRes,
                "drawable",
                holder.itemView.context.packageName
            )
        )

        holder.itemView.setOnClickListener { onChatClick(chat) }
    }

    override fun getItemCount(): Int = chatList.size

    fun updateData(newChatList: List<Chat>) {
        chatList = newChatList
        notifyDataSetChanged()
    }
}
