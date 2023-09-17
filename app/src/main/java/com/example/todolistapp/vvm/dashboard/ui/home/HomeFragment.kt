package com.example.todolistapp.vvm.dashboard.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.data.dbAuth.FirebaseAuth
import com.example.todolistapp.databinding.FragmentDashboardHomeBinding
import com.example.todolistapp.vvm.dashboard.DashboardActivity
import com.example.todolistapp.vvm.dashboard.Util
import com.example.todolistapp.vvm.dashboard.ui.adapter.HomeCategoryAdapter
import com.example.todolistapp.vvm.dashboard.ui.adapter.HomeTodoAdapter
import com.example.todolistapp.vvm.dashboard.ui.model.HomeTodoModel

class HomeFragment : Fragment() {

    private var _binding: FragmentDashboardHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dashboardActivity: DashboardActivity
    private val adapterCategory = HomeCategoryAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        initRecyclerView()
        initViews()

        return root
    }

    private fun initViews() {
        _binding?.apply {
            val shortestName: String = homeViewModel.getShortestName(FirebaseAuth.auth.currentUser!!.displayName!!.split(" "))
            tvDisplayName.text = shortestName

            val currentDate: String = homeViewModel.getCurrentDate()
            tvCurrentDate.text = currentDate

            btnAddCategory.setOnClickListener {
                val bottomSheetFragment = HomeAddBottomSheetFragment(requireContext(), adapterCategory)
                bottomSheetFragment.show(requireActivity().supportFragmentManager, "AddCategoryBottomDialog")
            }

            tvSeeAll.setOnClickListener {
                val seeAllBottomSheetFragment = HomeSeeAllBottomSheetFragment()
                seeAllBottomSheetFragment.show(requireActivity().supportFragmentManager, "SeeAllBottomDialog")
            }
        }
    }

    private fun initRecyclerView() {
        _binding?.apply {
            // category recyclerview
            adapterCategory.setList(Util.homeCategoryList)
            rvCategories.adapter = adapterCategory

            // todoList recyclerView
            val adapterTodo = HomeTodoAdapter(requireContext()) {

            }
            adapterTodo.setList(homeViewModel.getRecyclerViewData(adapterTodo))
            rvToDo.adapter = adapterTodo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dashboardActivity = context as DashboardActivity
    }
}