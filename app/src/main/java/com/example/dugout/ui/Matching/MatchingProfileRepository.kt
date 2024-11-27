package com.example.dugout.ui.Matching

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class MatchingProfileRepository {
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("Users").child("users")
    private val reviewsRef = database.getReference("Users").child("reviews")

    suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            val snapshot = usersRef.child(userId).get().await()

            if (!snapshot.exists()) {
                return null
            }

            val user = snapshot.getValue(User::class.java)
            if (user == null) {
                return null
            }

            val winRate = calculateWinRate(userId)
            val reviewCount = getReviewCount(userId)

            UserProfile(
                name = user.name,
                team = user.team,
                rating = user.rating,
                profile_message = user.profile_message,
                profileImageRes = user.profileImageRes,
                winRate = winRate,
                reviewCount = reviewCount
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserReviews(userId: String): List<Review> {
        val reviews = mutableListOf<Review>()
        return try {
            val snapshot = reviewsRef.orderByChild("to_user_id").equalTo(userId).get().await()

            if (!snapshot.exists()) {
                return reviews
            }

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

            reviews
        } catch (e: Exception) {
            reviews
        }
    }

    private suspend fun calculateWinRate(userId: String): Int {
        // 실제 승률 계산 로직을 구현하세요.
        return 70
    }

    private suspend fun getReviewCount(userId: String): Int {
        return try {
            val snapshot = reviewsRef.orderByChild("to_user_id").equalTo(userId).get().await()
            snapshot.childrenCount.toInt()
        } catch (e: Exception) {
            0
        }
    }
}
