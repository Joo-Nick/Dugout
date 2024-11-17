package com.example.dugout.ui.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dugout.R
import com.example.dugout.databinding.FragmentMatchingprofileBinding

@Suppress("DEPRECATION")
class MatchingProfileFragment : Fragment() {

    private var _binding: FragmentMatchingprofileBinding? = null
    private val binding get() = _binding!!
    private lateinit var matchingItem: MatchingItem
    private lateinit var viewModel: MatchingProfileViewModel

    companion object {
        private const val ARG_MATCHING_ITEM = "matching_item"

        fun newInstance(item: MatchingItem): MatchingProfileFragment {
            val fragment = MatchingProfileFragment()
            val args = Bundle()
            args.putParcelable(ARG_MATCHING_ITEM, item)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchingItem = arguments?.getParcelable(ARG_MATCHING_ITEM) ?: MatchingItem()

        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(MatchingProfileViewModel::class.java)
        viewModel.setMatchingItem(matchingItem)
    }

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

        // ViewModel 관찰하여 UI 업데이트
        observeViewModel()

        return root
    }

    private fun observeViewModel() {
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            // 사용자 정보로 UI 업데이트
            binding.profileName.text = "이름: ${user.name}"
            binding.profileTeam.text = "응원하는 팀: ${user.team}"
            binding.profileWinRate.text = "직관 승률: ${user.winRate}%"
            binding.profileReview.text = "평점: ${user.rating}점 (${user.reviewCount}명)"
            binding.profileIntro.text = user.profileMessage

            // 프로필 이미지 설정 (필요한 경우)
            val resourceId = resources.getIdentifier(
                user.profileImageRes,
                "drawable",
                context?.packageName
            )
            Glide.with(this)
                .load(resourceId)
                .placeholder(R.drawable.leejoon)
                .into(binding.profileImage)
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
