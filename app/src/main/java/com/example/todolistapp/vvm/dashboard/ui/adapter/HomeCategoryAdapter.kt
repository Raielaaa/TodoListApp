package com.example.todolistapp.vvm.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.FragmentDashboardCategoriesListItemBinding
import com.example.todolistapp.vvm.dashboard.ui.model.HomeCategoryModel

class HomeCategoryAdapter(

): RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder>() {
    inner class HomeCategoryViewHolder(private val _binding: FragmentDashboardCategoriesListItemBinding): RecyclerView.ViewHolder(_binding.root) {
        fun bind(homeCategoryModel: HomeCategoryModel) {
            _binding.apply {
                tvCategory.text = homeCategoryModel.category
                tvTask.text = homeCategoryModel.task
                ivIcon.setImageURI(homeCategoryModel.image)
            }
        }
    }

    private val listItem: ArrayList<HomeCategoryModel> = ArrayList()

    fun setList(homeCategoryModel: List<HomeCategoryModel>) {
        listItem.clear()
        listItem.addAll(homeCategoryModel)
    }

    fun addToList(homeCategoryModel: HomeCategoryModel) {
        listItem.add(homeCategoryModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryViewHolder {
        val binding = FragmentDashboardCategoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: HomeCategoryViewHolder, position: Int) {
        holder.bind(listItem[position])
    }
}