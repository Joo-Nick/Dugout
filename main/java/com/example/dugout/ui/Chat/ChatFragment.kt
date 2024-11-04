package com.example.dugout.ui.Chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dugout.R
import com.example.dugout.databinding.FragmentChatBinding
import com.example.dugout.model.Chat

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAcceptedAdapter: ChatAcceptedAdapter
    private lateinit var chatRequestAdapter: ChatRequestAdapter

    private val chatList = mutableListOf(
        Chat("윤동희", "네! 내일 봐요~~", "1분 전", R.drawable.yoon_small),
        Chat("이주은", "다음에 또 봐요:)", "1일 전", R.drawable.leejoon),
        Chat("오타니 쇼헤이", "한국 야구 재밌어요!", "3일 전", R.drawable.othani),
        Chat("김도영", "다음 번에는 제가 가보겠습니다~", "3일 전", R.drawable.kimdo0),
        Chat("하지원", "다음에도 같이 볼래요?", "일주일 전", R.drawable.hajiwon)
    )

    private val chatRequestList = mutableListOf(
        Chat("김정원", "수락 대기 중", "", R.drawable.kimjungwon),
        Chat("박담비", "수락 대기 중", "", R.drawable.dambi),
        Chat("구자욱", "수락 대기 중", "", R.drawable.koojawook)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 상단 채팅 목록 리사이클러뷰 설정
        chatAcceptedAdapter = ChatAcceptedAdapter(chatList)
        binding.recyclerViewChatAccepted.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewChatAccepted.adapter = chatAcceptedAdapter

        // 하단 채팅 요청 목록 리사이클러뷰 설정
        chatRequestAdapter = ChatRequestAdapter(chatRequestList) { request, isAccepted ->
            if (isAccepted) {
                chatList.add(request)  // 채팅 목록에 추가
                chatRequestList.remove(request)  // 채팅 요청 목록에서 제거
                chatAcceptedAdapter.notifyDataSetChanged()  // 채팅 목록 갱신
                chatRequestAdapter.notifyDataSetChanged()  // 채팅 요청 목록 갱신
            } else {
                chatRequestList.remove(request)  // 채팅 요청 목록에서 제거
                chatRequestAdapter.notifyDataSetChanged()  // 채팅 요청 목록 갱신
            }
        }
        binding.recyclerViewChatRequests.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChatRequests.adapter = chatRequestAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
