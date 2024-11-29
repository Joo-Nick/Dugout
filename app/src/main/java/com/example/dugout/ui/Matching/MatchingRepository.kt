package com.example.dugout.ui.Matching

import android.util.Log
import com.example.dugout.R
import com.google.firebase.Firebase
import com.google.firebase.database.childEvents
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class MatchingRepository {
    private val database = Firebase.database
    private val usersRef = database.getReference("Users").child("users")
    private val eventsRef = database.getReference("Users").child("events")

    suspend fun getAllMatchingItems(): List<MatchingItem> {
        val items = mutableListOf<MatchingItem>()

        val eventsSnapshot = eventsRef.get().await()
        for (eventData in eventsSnapshot.children) {
            val event = eventData.getValue(Event::class.java)
            if (event != null) {
                val userSnapshot = usersRef.child(event.user_id).get().await()
                val user = userSnapshot.getValue(User::class.java)
                if (user != null) {
                    val matchingItem = MatchingItem(
                        eventId = eventData.key ?: "",
                        userId = event.user_id,
                        name = user.name,
                        gender = user.gender,
                        profileMessage = user.profile_message,
                        team = user.team,
                        rating = user.rating,
                        profileImageRes = user.profileImageRes,
                        date = event.date,
                        stadium = event.stadium,
                        message = event.message,
                        ticket = event.ticket
                    )
                    items.add(matchingItem)
                }
            }
        }
        return items

    }

    // 평점 필터(높은 순)
    suspend fun getItemsSortedByRatingDesc(): List<MatchingItem> {
        val items = getAllMatchingItems()
        return items.sortedByDescending { it.rating }
    }

    // 평점 필터(낮은 순)
    suspend fun getItemsSortedByRatingAsc(): List<MatchingItem> {
        val items = getAllMatchingItems()
        return items.sortedBy { it.rating }
    }

    // 성별 필터
    suspend fun getItemsByGender(gender: String?): List<MatchingItem> {
        val items = getAllMatchingItems()
        return gender?.let { items.filter { it.gender.equals(gender, ignoreCase = true) } } ?: items
    }

    // 티켓 필터
    suspend fun getItemsByTicketAvailability(hasTicket: Boolean?): List<MatchingItem> {
        val items = getAllMatchingItems()
        return hasTicket?.let { items.filter { it.ticket == hasTicket } } ?: items
    }

    // 날짜 필터(오래된 순)
    suspend fun getItemsSortedByDateAsc(): List<MatchingItem> {
        val items = getAllMatchingItems()
        return items.sortedBy { it.date }
    }

    // 날짜 필터(최신 순)
    suspend fun getItemsSortedByDateDesc(): List<MatchingItem> {
        val items = getAllMatchingItems()
        return items.sortedByDescending { it.date }
    }
}