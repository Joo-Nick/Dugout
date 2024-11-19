package com.example.dugout.ui.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dugout.R
import com.example.dugout.databinding.FragmentProfileBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 바인딩 초기화
        btnSave = binding.btnSave
        edtName = binding.edtName
        edtTeam = binding.edtTeam
        edtProfileMessage = binding.edtProfileMessage
        txtRating = binding.txtRating
        imgProfileImage = binding.imgProfile


        // ViewModel에서 프로필 데이터 가져오기
        profileViewModel.fetchProfile()

        // LiveData 옵저빙하여 데이터가 변경될 때마다 UI 업데이트
        profileViewModel.profile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                // Firebase에서 가져온 데이터를 UI에 반영
                edtName.setText(it.name)
                edtTeam.setText(it.team)
                edtProfileMessage.setText(it.profile_message)
                txtRating.text = it.rating

                val imageResId = resources.getIdentifier(
                    it.profileImageRes,
                    "drawable",
                    requireContext().packageName
                )

                if (imageResId != 0) {
                    // 리소스 ID가 존재하면 이미지를 로드
                    Glide.with(requireContext())
                        .load(imageResId)  // drawable 리소스 ID로 이미지를 로드
                        .into(imgProfileImage)
                } else {
                    // 기본 이미지로 대체 (리소스가 없을 경우)
                    Glide.with(requireContext())
                        .load(R.drawable.yoon_small)  // 기본 이미지 리소스 사용
                        .into(imgProfileImage)
                }
            }
        }

        // Save 버튼 클릭 시
        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val team = edtTeam.text.toString()
            val profileMessage = edtProfileMessage.text.toString()

            if (name.isEmpty() || team.isEmpty() || profileMessage.isEmpty()) {
                Toast.makeText(requireContext(), "모든 필드를 채워주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // Firebase로 데이터 전송
                profileViewModel.sendProfile(name, "", profileMessage, team)

                // 상태 업데이트를 옵저빙하여 Toast로 알림
                profileViewModel.profileUpdateStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
