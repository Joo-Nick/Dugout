package com.example.dugout.ui.Profile

import android.provider.ContactsContract.Profile
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseError

class ProfileRepository() {
    val database = Firebase.database
    val profileRef: DatabaseReference = database.getReference("Users").child("users").child("user1")

    fun getProfile(callback: (ProfileItem?) -> Unit) {
        profileRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").getValue(String::class.java)
                val profileImageRes = snapshot.child("profileImageRes").getValue(String::class.java)
                val profileMessage = snapshot.child("profile_message").getValue(String::class.java)
                val team = snapshot.child("team").getValue(String::class.java)

                if (name != null && profileImageRes != null && profileMessage != null && team != null) {
                    val profileItem = ProfileItem(
                        name = name,
                        profileImageRes = profileImageRes,
                        profile_message = profileMessage,
                        team = team,
                        gender = "",
                        rating = ""
                    )
                    callback(profileItem) // 단일 프로필 반환
                } else {
                    callback(null) // 데이터가 없으면 null 반환
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfileRepository", "Failed to read value.", error.toException())
                callback(null) // 오류 발생 시 null 반환
            }
        })
    }


    fun sendProfile(profile: ProfileItem) {
        val profileUpdate = mapOf(
            "name" to profile.name,
            "profileImageRes" to profile.profileImageRes,
            "profile_message" to profile.profile_message,
            "team" to profile.team
        )

        profileRef.updateChildren(profileUpdate).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 성공적으로 프로필이 업데이트됨
                Log.d("sendprofile", "Profile updated successfully!")
            } else {
                // 업데이트 실패 시
                Log.e("sendprofile", "Profile update failed", task.exception)
            }
        }
    }
}