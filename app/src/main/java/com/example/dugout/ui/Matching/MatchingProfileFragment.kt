package com.example.dugout.ui.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dugout.R
import com.example.dugout.databinding.FragmentMatchingprofileBinding

@Suppress("DEPRECATION")
class MatchingProfileFragment : Fragment() {

    private var _binding: FragmentMatchingprofileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MatchingProfileViewModel by viewModels()
    private val args: MatchingProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMatchingprofileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.reviewRecyclerView.adapter = ReviewAdapter(emptyList())

        // ViewModel 관찰하여 UI 업데이트
        observeViewModel()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = args.userId
        viewModel.fetchUser(userId)
    }

    private fun observeViewModel() {
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            // 사용자 정보로 UI 업데이트
            binding.profileName.text = "이름: ${user.name}"
            binding.profileTeam.text = "응원하는 팀: ${user.team}"
            binding.profileWinRate.text = "직관 승률: ${user.winRate}%"
            binding.profileReview.text = "평점: ${user.rating}점 (${user.reviewCount}명)"
            binding.profileIntro.text = user.profile_message

            // 프로필 이미지 설정 (필요한 경우)
            val resourceId = resources.getIdentifier(
                user.profileImageRes,
                "drawable",
                requireContext().packageName
            )
            Glide.with(this)
                .load(resourceId)
                .placeholder(R.drawable.leejoon)
                .into(binding.profileImage)
            if (resourceId != 0) { // 유효한 리소스 ID인지 확인
                Glide.with(this)
                    .load(resourceId)
                    .placeholder(R.drawable.leejoon)
                    .into(binding.profileImage)
            } else {
                // 리소스 ID가 유효하지 않을 경우 기본 이미지 사용
                Glide.with(this)
                    .load(R.drawable.leejoon)
                    .into(binding.profileImage)
            }
        }

        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            val adapter = ReviewAdapter(reviews)
            binding.reviewRecyclerView.adapter = adapter
            binding.reviewRecyclerView.layoutManager = LinearLayoutManager(context)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
