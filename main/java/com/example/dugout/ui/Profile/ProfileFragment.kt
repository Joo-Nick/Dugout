package com.example.dugout.ui.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dugout.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Spinner 설정 및 어댑터 추가
        val stadiums = listOf(
            "광주-기아 챔피언스 필드",
            "대구 삼성 라이온즈 파크",
            "서울종합운동장 야구장",
            "수원 케이티 위즈 파크",
            "인천 SSG 랜더스필드",
            "사직 야구장",
            "베이스볼 드림파크",
            "창원 NC 파크",
            "고척 스카이돔"
        )

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            stadiums
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spinnerStadiums.adapter = spinnerAdapter

        // Spinner 아이템 선택 리스너 설정
        binding.spinnerStadiums.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (view != null) {
                    val selectedStadium = parent.getItemAtPosition(position).toString()
                    binding.textStadiumSelect.text = selectedStadium
                } else {
                    // 로그 메시지 추가
                    Log.e("ProfileFragment", "View is null in onItemSelected")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 선택이 없을 때의 동작
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
