package com.example.dugout.ui.Matching

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class MatchingProfileRepository {
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("Users").child("users")
    private val reviewsRef = database.getReference("Users").child("reviews")

    suspend fun getUserProfile(userId: String): UserProfile {
        val snapshot = usersRef.child(userId).get().await()
        val user = snapshot.getValue(User::class.java)

        val winRate = calculateWinRate(userId)
        val reviewCount = getReviewCount(userId)

        return UserProfile(
            name = user?.name ?: "",
            team = user?.team ?: "",
            rating = user?.rating ?: 0.0,
            profileMessage = user?.profile_message ?: "",
            profileImageRes = user?.profileImageRes ?: "default_profile",
            winRate = winRate,
            reviewCount = reviewCount
        )
    }

    suspend fun getUserReviews(userId: String): List<Review> {
        val reviews = mutableListOf<Review>()
        val snapshot = reviewsRef.orderByChild("to_user_id").equalTo(userId).get().await()
        for (reviewData in snapshot.children) {
            val review = reviewData.getValue(Review::class.java)
            if (review != null) {
                // 리뷰 작성자 이름 가져오기
                val fromUserSnapshot = usersRef.child(review.from_user_id).get().await()
                val fromUser = fromUserSnapshot.getValue(User::class.java)
                val fromUserName = fromUser?.name ?: "알 수 없는 사용자"

                reviews.add(review.copy(fromUserName = fromUserName))
            }
        }
        return reviews
    }

    private suspend fun calculateWinRate(userId: String): Int {
        return 70
    }

    private suspend fun getReviewCount(userId: String): Int {
        val snapshot = reviewsRef.orderByChild("to_user_id").equalTo(userId).get().await()
        return snapshot.childrenCount.toInt()
    }
}
