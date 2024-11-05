package com.example.dugout.ui.Chatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R

class ChattingAdapter(var messages: ArrayList<String>) : RecyclerView.Adapter<ChattingAdapter.Holder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chatting, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    class Holder(private val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(message: String){
            val messageTextView: TextView = view.findViewById(R.id.txt_message)
            messageTextView.text = message
        }
    }
}