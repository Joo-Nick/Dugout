package com.example.dugout.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dugout.databinding.FragmentChatBinding
import com.example.dugout.ui.Chatting.ChattingActivity
import com.example.dugout.ui.adapter.ChatAcceptedAdapter
import com.example.dugout.ui.adapter.ChatRequestAdapter
import com.example.dugout.viewmodel.ChatViewModel

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAcceptedAdapter: ChatAcceptedAdapter
    private lateinit var chatRequestAdapter: ChatRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        setupAdapters() // Adapters 초기화 및 RecyclerView 설정
        observeViewModel() // LiveData 관찰

        return binding.root
    }

    private fun setupAdapters() {
        // AcceptedChats 어댑터
        chatAcceptedAdapter = ChatAcceptedAdapter(emptyList()) { chat ->
            val intent = Intent(requireContext(), ChattingActivity::class.java)
            intent.putExtra("chatId", chat.id)
            intent.putExtra("chatName", chat.name)
            startActivity(intent)
        }
        binding.recyclerViewChatAccepted.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAcceptedAdapter
        }

        // ChatRequests 어댑터
        chatRequestAdapter = ChatRequestAdapter(emptyList()) { request, isAccepted ->
            if (isAccepted) {
                chatViewModel.acceptRequest(request)
            } else {
                chatViewModel.declineRequest(request)
            }
        }
        binding.recyclerViewChatRequests.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatRequestAdapter
        }
    }

    private fun observeViewModel() {
        chatViewModel.acceptedChats.observe(viewLifecycleOwner) { acceptedChats ->
            chatAcceptedAdapter.updateData(acceptedChats)
        }

        chatViewModel.chatRequests.observe(viewLifecycleOwner) { chatRequests ->
            chatRequestAdapter.updateData(chatRequests)
        }
    }

}