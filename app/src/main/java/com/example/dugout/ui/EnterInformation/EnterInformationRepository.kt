package com.example.dugout.repository

import com.example.dugout.model.EnterInformation
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class EnterInformationRepository {
    private val database = FirebaseDatabase.getInstance()
    private val enterInfoRef = database.getReference("Users/events")

    // EnterInformation 저장
    suspend fun saveEnterInformation(info: EnterInformation) {
        try {
            enterInfoRef.push().setValue(info).await()
        } catch (e: Exception) {
            throw e // 예외를 상위로 전달
        }
    }
}