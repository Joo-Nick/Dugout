package com.example.dugout.ui.Matching

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dugout.R

class MatchingAdapter(private val itemList: List<MatchingItem>,private val itemClickListener: (MatchingItem) -> Unit) : RecyclerView.Adapter<MatchingAdapter.MatchingViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    inner class MatchingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.profile_image)
        val nameText: TextView = view.findViewById(R.id.name_text)
        val ratingText: TextView = view.findViewById(R.id.rating_text)
        val messageText: TextView = view.findViewById(R.id.message_text)

        @SuppressLint("DiscouragedApi")
        fun bind(item: MatchingItem) {
            nameText.text = item.name
            ratingText.text = "평점 ${item.rating} / 5"
            messageText.text = item.message

            val resourceId = profileImage.context.resources.getIdentifier(
                item.profileImageRes,
                "drawable",
                profileImage.context.packageName
            )

            Glide.with(profileImage.context)
                .load(resourceId)
                .placeholder(R.drawable.leejoon) // 기본 이미지 설정
                .into(profileImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matching, parent, false)
        return MatchingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchingViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.itemView.setOnClickListener{myItemClickListener.onItemClick()}
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}