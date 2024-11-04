package com.example.dugout.ui.Matching

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dugout.MainActivity
import com.example.dugout.R
import com.example.dugout.databinding.FragmentMatchingBinding

class MatchingFragment : Fragment() {

    private var _binding: FragmentMatchingBinding? = null
    private val binding get() = _binding!!
    private var itemList = mutableListOf<MatchingItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val matchingViewModel = ViewModelProvider(this).get(MatchingViewModel::class.java)

        _binding = FragmentMatchingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 더미 데이터
        itemList = mutableListOf(
            MatchingItem(R.drawable.yoon_small, "윤동희", 4.9, "롯데 응원하는 분 같이 보러가요~~"),
            MatchingItem(R.drawable.kimdo0, "김도영", 4.8, "삼성 팬들 함께 응원합시다!"),
            MatchingItem(R.drawable.elwha, "최홍라", 5.0, "롯데 팬인데 같이 갈 사람 구해요!"),
            MatchingItem(R.drawable.leejoon, "이주은", 4.5, "같이 갈 사람 구해요!"),
            MatchingItem(R.drawable.dambi, "박담비", 4.4, "담비에유!"),
            MatchingItem(R.drawable.othani, "오타니 쇼헤이", 4.1, "일본 팬인데 같이 갈 사람 구해요! 연락 주세요."),
            MatchingItem(R.drawable.hajiwon, "하지원", 4.3, "한화 팬인데 같이 갈 사람 구해요! 나는 행복합니다~~"),
            MatchingItem(R.drawable.kimjungwon, "김정원", 4.0, "nc 팬인데 같이 갈 사람 구해요!"),
            MatchingItem(R.drawable.yoon_small, "구자욱", 3.0, "삼성 팬인데 같이 갈 사람 구해요! 삼성의 보물 내야수 김영웅 파이팅"),
            MatchingItem(R.drawable.yoon_small, "구자욱", 2.0, "삼성 팬인데 같이 갈 사람 구해요! 삼성의 보물 내야수 김영웅 파이팅")

        )

        // 어댑터 초기화
        val adapter = MatchingAdapter(itemList) { _ ->
        }
        adapter.setMyItemClickListener(object: MatchingAdapter.MyItemClickListener{
            override fun onItemClick() {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main,MatchingProfileFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // 스피너 설정
        val spinner: Spinner = binding.ratingSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rating_options,
            android.R.layout.simple_spinner_item
        ).also{ arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> {
                        // 높은 평점 순
                        itemList.sortByDescending { it.rating }
                    }

                    2 -> {
                        // 낮은 평점 순
                        itemList.sortBy { it.rating }
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 기본 동작 없음
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}