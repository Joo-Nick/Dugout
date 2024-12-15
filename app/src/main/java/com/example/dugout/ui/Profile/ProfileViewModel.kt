package com.example.dugout.ui.Profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _profile = MutableLiveData<ProfileItem?>()
    val profile: LiveData<ProfileItem?> get() = _profile


    private val _profileUpdateStatus = MutableLiveData<String>()
    val profileUpdateStatus: LiveData<String> get() = _profileUpdateStatus

    private val repository = ProfileRepository()

    fun fetchProfile() {
        repository.getProfile { profile ->
            _profile.value = profile
        }
    }

    fun sendProfile(name: String, profileImageRes: String, profile_message: String, team: String) {
        val newProfile = ProfileItem(
            name = name,
            profileImageRes = profileImageRes,
            profile_message = profile_message,
            team = team,
            gender = "",
            rating = 0.0
        )
        repository.sendProfile(newProfile)
    }

    fun uploadProfileImage(uri: Uri, onUploadComplete: (String) -> Unit) {
        repository.uploadImage(uri) { imageUrl ->
            if (imageUrl != null) {
                onUploadComplete(imageUrl)
            } else {
                _profileUpdateStatus.value = "이미지 업로드 실패"
            }
        }
    }
}
