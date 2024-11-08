// EnterInformationFragment.kt
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
import androidx.navigation.fragment.findNavController
import com.example.dugout.R
import com.example.dugout.databinding.FragmentEnterInformationBinding //

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
        _binding = FragmentEnterInformationBinding.inflate(inflater, container, false) //
        val root: View = binding.root

        // 뒤로 가기 버튼 설정
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_enterInformationFragment_to_chatFragment)
        }

        // SharedPreferences 초기화
        sharedPreferences = requireContext().getSharedPreferences("EnterInformationData", Context.MODE_PRIVATE)

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

        // 저장된 데이터 로드 및 UI에 반영
        loadEnterInformationData()

        // Spinner 아이템 선택 리스너 설정
        binding.spinnerStadiums.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedStadium = parent.getItemAtPosition(position).toString()
                binding.textStadiumSelect.text = "선택된 구장: $selectedStadium"
                sharedPreferences.edit().putString("selectedStadium", selectedStadium).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 선택이 없을 때의 동작
            }
        }

        // CalendarView 날짜 선택 리스너 설정
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            binding.textDateSelect.text = "선택된 날짜: $selectedDate"
            sharedPreferences.edit().putString("selectedDate", selectedDate).apply()
        }

        // 신청하기 버튼 클릭 시 데이터 저장
        binding.submitButton.setOnClickListener {
            saveEnterInformationData()
            Toast.makeText(context, "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun saveEnterInformationData() {
        val editor = sharedPreferences.edit()

        // 선택된 구장과 날짜는 리스너에서 이미 저장됨
        // 티켓 유무 저장
        val hasTicket = binding.radioGroupTicket.checkedRadioButtonId == R.id.radioTicketYes
        editor.putBoolean("hasTicket", hasTicket)

        // 성별 저장
        val isMale = binding.radioGroupGender.checkedRadioButtonId == R.id.radioGenderMale
        editor.putBoolean("isMale", isMale)

        editor.apply()
    }

    private fun loadEnterInformationData() {
        // 저장된 구장 로드
        val selectedStadium = sharedPreferences.getString("selectedStadium", "선택된 구장: 없음")
        binding.textStadiumSelect.text = "선택된 구장: $selectedStadium"
        val stadiumPosition = (binding.spinnerStadiums.adapter as ArrayAdapter<String>).getPosition(selectedStadium)
        binding.spinnerStadiums.setSelection(stadiumPosition)

        // 저장된 날짜 로드
        val selectedDate = sharedPreferences.getString("selectedDate", "선택된 날짜: 없음")
        binding.textDateSelect.text = "선택된 날짜: $selectedDate"
        if (selectedDate != "선택된 날짜: 없음") {
            val parts = selectedDate!!.split("-")
            if (parts.size == 3) {
                val year = parts[0].toInt()
                val month = parts[1].toInt() - 1
                val day = parts[2].toInt()
                val calendar = java.util.Calendar.getInstance()
                calendar.set(year, month, day)
                binding.calendarView.date = calendar.timeInMillis
            }
        }

        // 저장된 티켓 유무 로드
        val hasTicket = sharedPreferences.getBoolean("hasTicket", false)
        binding.radioGroupTicket.check(if (hasTicket) R.id.radioTicketYes else R.id.radioTicketNo)

        // 저장된 성별 로드
        val isMale = sharedPreferences.getBoolean("isMale", true)
        binding.radioGroupGender.check(if (isMale) R.id.radioGenderMale else R.id.radioGenderFemale)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
