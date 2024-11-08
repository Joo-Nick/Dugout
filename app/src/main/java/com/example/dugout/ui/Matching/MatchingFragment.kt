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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dugout.MainActivity
import com.example.dugout.R
import com.example.dugout.databinding.FragmentMatchingBinding

class MatchingFragment : Fragment() {

    private var _binding: FragmentMatchingBinding? = null
    private val binding get() = _binding!!
    private val matchingViewModel: MatchingViewModel by viewModels()
    private var itemList = mutableListOf<MatchingItem>()
    private lateinit var adapter: MatchingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupSpinners()
        observeViewModel()

        return root
    }

    private fun setupRecyclerView(){
        adapter = MatchingAdapter(itemList) { _ -> }

        adapter.setMyItemClickListener(object : MatchingAdapter.MyItemClickListener {
            override fun onItemClick() {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, MatchingProfileFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })

        // RecyclerView에 어댑터와 레이아웃 매니저 설정
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSpinner(spinner: Spinner, arrayResource: Int, onItemSelected: (Int) -> Unit) {
        ArrayAdapter.createFromResource(
            requireContext(),
            arrayResource,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
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
                onItemSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 기본 동작 없음
            }
        }
    }


    private fun setupSpinners() {
        // 평점 정렬 Spinner 설정
        setupSpinner(
            binding.ratingSpinner,
            R.array.rating_options,
            onItemSelected = { position ->
                when (position) {
                    1 -> matchingViewModel.loadItemsByRatingDesc()  // 높은 평점 순
                    2 -> matchingViewModel.loadItemsByRatingAsc()   // 낮은 평점 순
                }
            }
        )

        // 성별 필터 Spinner 설정
        setupSpinner(
            binding.genderSpinner,
            R.array.gender_options,
            onItemSelected = { position ->
                when (position) {
                    1 -> matchingViewModel.loadItemsByGender("M")  // 남성만
                    2 -> matchingViewModel.loadItemsByGender("F")  // 여성만
                    else -> matchingViewModel.loadItemsByGender(null) // 전체
                }
            }
        )

        // 티켓 유무 필터 Spinner 설정
        setupSpinner(
            binding.ticketSpinner,
            R.array.ticket_options,
            onItemSelected = { position ->
                when (position) {
                    1 -> matchingViewModel.loadItemsByTicketAvailability(true)  // 티켓 소지자
                    2 -> matchingViewModel.loadItemsByTicketAvailability(false) // 티켓 미소지자
                    else -> matchingViewModel.loadItemsByTicketAvailability(null) // 전체
                }
            }
        )

        // 날짜 정렬 Spinner 설정
        setupSpinner(
            binding.dateSpinner,
            R.array.date_options,
            onItemSelected = { position ->
                when (position) {
                    1 -> matchingViewModel.loadItemsByDateAsc()   // 오래된 순
                    2 -> matchingViewModel.loadItemsByDateDesc()  // 최신 순
                }
            }
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel(){
        matchingViewModel.matchingItems.observe(viewLifecycleOwner) { items ->
            itemList.clear()
            itemList.addAll(items)
            adapter.    notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}