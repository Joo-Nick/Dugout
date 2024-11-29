package com.example.dugout.ui.EnterInformation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dugout.R
import com.example.dugout.databinding.FragmentEnterInformationBinding
import com.example.dugout.model.EnterInformation
import com.example.dugout.viewmodel.EnterInformationViewModel

class EnterInformationFragment : Fragment() {
    private var _binding: FragmentEnterInformationBinding? = null
    private val binding get() = _binding!!
    private lateinit var enterInformationViewModel: EnterInformationViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enterInformationViewModel = ViewModelProvider(this).get(EnterInformationViewModel::class.java)
        _binding = FragmentEnterInformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 뒤로 가기 버튼
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // SharedPreferences 초기화
        sharedPreferences = requireContext().getSharedPreferences("EnterInformationData", Context.MODE_PRIVATE)

        // Spinner 설정
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
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, stadiums)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStadiums.adapter = spinnerAdapter

        // Spinner 선택 리스너
        binding.spinnerStadiums.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedStadium = parent.getItemAtPosition(position).toString()
                binding.textStadiumSelect.text = "선택된 구장: $selectedStadium"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // CalendarView 날짜 선택 리스너
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            binding.textDateSelect.text = "선택된 날짜: $selectedDate"
        }

        // 신청하기 버튼
        binding.submitButton.setOnClickListener {
            val selectedDate = binding.textDateSelect.text.toString()
            val selectedStadium = binding.textStadiumSelect.text.toString()
            val message = binding.editMessage.text.toString()
            val hasTicket = binding.radioGroupTicket.checkedRadioButtonId == R.id.radioTicketYes
            val gender = if (binding.radioGroupGender.checkedRadioButtonId == R.id.radioGenderMale) "남자" else "여자"

            if (selectedDate.isNotEmpty() && selectedStadium.isNotEmpty() && message.isNotEmpty()) {
                val info = EnterInformation(
                    date = selectedDate,
                    stadium = selectedStadium,
                    message = message,
                    ticket = hasTicket,
                    gender = gender
                )
                enterInformationViewModel.saveEnterInformation(info)
                Toast.makeText(context, "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
