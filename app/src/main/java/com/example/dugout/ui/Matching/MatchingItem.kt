package com.example.dugout.ui.Matching

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchingItem(
    val eventId: String = "",
    val userId: String = "",
    val name: String = "",
    val gender: String = "",
    val profileMessage: String = "",
    val team: String = "",
    val rating: Double = 0.0,
    val profileImageRes: String = "",
    val date: String = "",
    val stadium: String = "",
    val message: String = "",
    val ticket: Boolean = false
) : Parcelable


