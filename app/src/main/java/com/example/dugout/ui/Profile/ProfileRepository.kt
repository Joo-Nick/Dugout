package com.example.dugout.ui.Profile

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileRepository() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child("users").child("user1")
    private val storage: StorageReference = FirebaseStorage.getInstance().getReference("profile_images")

    fun getProfile(callback: (ProfileItem?) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").getValue(String::class.java)
                val profileImageRes = snapshot.child("profileImageRes").getValue(String::class.java)
                val profileMessage = snapshot.child("profile_message").getValue(String::class.java)
                val team = snapshot.child("team").getValue(String::class.java)
                val rating = snapshot.child("rating").getValue(Double::class.java)

                if (name != null && profileImageRes != null && profileMessage != null && team != null && rating != null) {
                    val profileItem = ProfileItem(
                        name = name,
                        profileImageRes = profileImageRes,
                        profile_message = profileMessage,
                        team = team,
                        gender = "",
                        rating = rating
                    )
                    callback(profileItem)
                } else {
                    callback(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfileRepository", "Failed to read value.", error.toException())
                callback(null)
            }
        })
    }


    fun sendProfile(profile: ProfileItem) {
        val profileData = mapOf(
            "name" to profile.name,
            "profileImageRes" to profile.profileImageRes,
            "profile_message" to profile.profile_message,
            "team" to profile.team
        )

        database.updateChildren(profileData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("sendprofile", "Profile updated successfully!")
            } else {
                Log.e("sendprofile", "Profile update failed", task.exception)
            }
        }
    }
        fun uploadImage(uri: Uri, onUploadComplete: (String?) -> Unit) {
            val fileRef = storage.child(System.currentTimeMillis().toString() + ".jpg")
            fileRef.putFile(uri).addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    onUploadComplete(downloadUrl.toString())
                }
            }.addOnFailureListener {
                Log.e("ProfileRepository", "Image upload failed.", it)
                onUploadComplete(null)
            }
        }
    }
