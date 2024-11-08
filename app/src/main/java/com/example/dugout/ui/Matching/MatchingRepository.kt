package com.example.dugout.ui.Matching

import android.util.Log
import com.example.dugout.R
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class MatchingRepository {
    val database = Firebase.database
    val matchingItemsRef = database.getReference("matchingItems")

    private suspend fun getAllMatchingItems(): List<MatchingItem> {
        val items = mutableListOf<MatchingItem>()
        val snapshot = matchingItemsRef.get().await()
        for (data in snapshot.children) {
            data.getValue(MatchingItem::class.java)?.let { items.add(it) }
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