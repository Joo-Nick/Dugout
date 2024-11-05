package com.example.dugout.ui.Chatting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dugout.databinding.FragmentChatBinding
import com.example.dugout.databinding.FragmentChattingBinding
import com.example.dugout.ui.Chat.ChatViewModel

class ChattingFragment : Fragment() {

    private var _binding: FragmentChattingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chattingViewModel =
            ViewModelProvider(this).get(ChattingViewModel::class.java)

        _binding = FragmentChattingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}