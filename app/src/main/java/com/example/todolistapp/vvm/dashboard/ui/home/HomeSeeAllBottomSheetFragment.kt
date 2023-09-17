package com.example.todolistapp.vvm.dashboard.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentHomeSeeAllBottomSheetBinding
import com.example.todolistapp.vvm.dashboard.Util
import com.example.todolistapp.vvm.dashboard.ui.adapter.HomeTodoAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * A simple [Fragment] subclass.
 * Use the [HomeSeeAllBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeSeeAllBottomSheetFragment() : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeSeeAllBottomSheetBinding.inflate(inflater, container, false)

        binding.apply {
            val adapterTodo = HomeTodoAdapter(requireContext()) {

            }
            adapterTodo.setList(HomeViewModel().getRecyclerViewData(adapterTodo))
            rvMain.adapter = adapterTodo
        }

        return binding.root
    }
}