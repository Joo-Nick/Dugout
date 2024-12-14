package com.example.dugout.ui.Profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dugout.R
import com.example.dugout.databinding.FragmentProfileBinding
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var btnSave: Button
    private lateinit var edtTeam: EditText
    private lateinit var edtName: EditText
    private lateinit var edtProfileMessage: EditText
    private lateinit var txtRating: TextView
    private lateinit var imgProfileImage: ImageView
    private lateinit var imgPickerLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        btnSave = binding.btnSave
        edtName = binding.edtName
        edtTeam = binding.edtTeam
        edtProfileMessage = binding.edtProfileMessage
        txtRating = binding.txtRating
        imgProfileImage = binding.imgProfile

        imgPickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // 선택한 이미지의 URI 가져오기
                selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    // URI를 ImageView에 로드
                    Glide.with(requireContext())
                        .load(selectedImageUri)
                        .placeholder(R.drawable.dambi) // 로드 중 표시될 이미지
                        .error(R.drawable.leejoon) // 로드 실패 시 표시될 이미지
                        .into(imgProfileImage)
                } else {
                    Toast.makeText(requireContext(), "이미지를 선택하지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "이미지 선택이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imgPickerLauncher.launch(intent)
        }

        profileViewModel.fetchProfile()

        // LiveData 옵저빙하여 데이터가 변경될 때마다 UI 업데이트
        profileViewModel.profile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                edtName.setText(it.name)
                edtTeam.setText(it.team)
                edtProfileMessage.setText(it.profile_message)
                txtRating.text = it.rating

                if (it.profileImageRes.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load(it.profileImageRes)
                        .placeholder(R.drawable.hajiwon)
                        .error(R.drawable.koojawook)
                        .into(imgProfileImage)
                    Log.d("ProfileFragment", "Profile Image URL: ${it.profileImageRes}")
                } else {
                    imgProfileImage.setImageResource(R.drawable.yoon_small)
                }
            }
        }

        // Save 버튼 클릭 시
        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val team = edtTeam.text.toString()
            val profileMessage = edtProfileMessage.text.toString()

            if (selectedImageUri != null) {
                profileViewModel.uploadProfileImage(selectedImageUri!!) { imageUrl ->
                    profileViewModel.sendProfile(name, imageUrl, profileMessage, team)
                }
            } else {
                val currentImageUrl = profileViewModel.profile.value?.profileImageRes ?: ""
                profileViewModel.sendProfile(name, currentImageUrl, profileMessage, team)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}