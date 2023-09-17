package com.example.todolistapp.vvm.dashboard.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentDashboardAddTodoBinding

class AddTodoFragment : Fragment() {
    private lateinit var addTodoViewModel: AddTodoViewModel
    private var _binding: FragmentDashboardAddTodoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addTodoViewModel = ViewModelProvider(this)[AddTodoViewModel::class.java]

        _binding = FragmentDashboardAddTodoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViews()

        return root
    }

    private fun initViews() {
        _binding?.apply {
            addTodoViewModel.configureRealTimeCounter(tvCurrentTime, this@AddTodoFragment)

            btnSetdate.setOnClickListener {
                addTodoViewModel.setDate(requireContext(), btnSetdate)
            }
            btnSetTime.setOnClickListener {
                addTodoViewModel.setTime(this@AddTodoFragment, btnSetTime)
            }
            actvCategories.setAdapter(ArrayAdapter(requireContext(), R.layout.dashboard_add_todo_dropdown_item, resources.getStringArray(R.array.category)))

            mcvAddTask.setOnClickListener {
                addTodoViewModel.addToDB(etTitle, etSubtitle, etNotes, btnSetTime, btnSetdate, etCategory, requireContext(), requireActivity())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}