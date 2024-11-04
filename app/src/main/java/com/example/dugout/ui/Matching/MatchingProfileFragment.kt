package com.example.dugout.ui.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dugout.databinding.FragmentMatchingprofileBinding

class MatchingProfileFragment : Fragment() {

    private var _binding: FragmentMatchingprofileBinding? = null
    private val binding get() = _binding!!

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}