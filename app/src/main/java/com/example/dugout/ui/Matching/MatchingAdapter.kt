    package com.example.dugout.ui.Matching

    import android.annotation.SuppressLint
    import android.net.Uri
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.dugout.R

    class MatchingAdapter(private val itemList: List<MatchingItem>,private val itemClickListener: (String) -> Unit) : RecyclerView.Adapter<MatchingAdapter.MatchingViewHolder>(){

        inner class MatchingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val profileImage: ImageView = view.findViewById(R.id.profile_image)
            val nameText: TextView = view.findViewById(R.id.name_text)
            val ratingText: TextView = view.findViewById(R.id.rating_text)
            val messageText: TextView = view.findViewById(R.id.message_text)

            @SuppressLint("DiscouragedApi")
            fun bind(item: MatchingItem) {
                nameText.text = item.name
                messageText.text = item.message
                ratingText.text = "평점 ${item.rating} / 5"
//                teamText.text = item.team
//                dateText.text = item.date
//                stadiumText.text = item.stadium

                val resourceId = profileImage.context.resources.getIdentifier(
                    item.profileImageRes,
                    "drawable",
                    profileImage.context.packageName
                )

                Glide.with(profileImage.context)
                    .load(resourceId)
                    .placeholder(R.drawable.leejoon) // 기본 이미지 설정
                    .into(profileImage)

                itemView.setOnClickListener{
                    Log.d("MatchingAdapter", "Clicked userId: ${item.userId}")
                    itemClickListener(item.userId)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matching, parent, false)
            return MatchingViewHolder(view)
        }

        override fun onBindViewHolder(holder: MatchingViewHolder, position: Int) {
            val item = itemList[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }
    }