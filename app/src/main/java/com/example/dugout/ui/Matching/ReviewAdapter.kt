package com.example.dugout.ui.Matching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dugout.R

class ReviewAdapter(private val reviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reviewerName: TextView = view.findViewById(R.id.reviewerNameTextView)
        val reviewRating: TextView = view.findViewById(R.id.reviewRatingTextView)
        val reviewComment: TextView = view.findViewById(R.id.reviewCommentTextView)

        fun bind(review: Review) {
            reviewerName.text = review.fromUserName
            reviewRating.text = "${review.rating}점"
            reviewComment.text = review.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    override fun getItemCount(): Int = reviewList.size
}
