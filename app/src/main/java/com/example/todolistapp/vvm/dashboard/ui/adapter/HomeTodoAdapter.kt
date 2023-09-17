package com.example.todolistapp.vvm.dashboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.FragmentDashboardTodoListItemBinding
import com.example.todolistapp.vvm.dashboard.ui.model.HomeCategoryModel
import com.example.todolistapp.vvm.dashboard.ui.model.HomeTodoModel

class HomeTodoAdapter(
    private val context: Context,
    private val clickLister: (HomeTodoModel) -> Unit
): RecyclerView.Adapter<HomeTodoAdapter.HomeTodoViewHolder>() {
    inner class HomeTodoViewHolder(private val _binding: FragmentDashboardTodoListItemBinding): RecyclerView.ViewHolder(_binding.root) {
        fun bind(homeTodoModel: HomeTodoModel, clickLister: (HomeTodoModel) -> Unit) {
            _binding.apply {
                tvHomeCategory.text = homeTodoModel.category
                tvTitle.text = homeTodoModel.topic
                tvSubTopic.text = homeTodoModel.subTopic
                tvTime.text = homeTodoModel.time

                root.setOnClickListener {
                    clickLister(homeTodoModel)
                }

                ivDelete.setOnClickListener {
                    collection.remove(homeTodoModel)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Item successfully deleted", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val collection: ArrayList<HomeTodoModel> = ArrayList()

    fun setList(homeTodoModel: List<HomeTodoModel>) {
        collection.clear()
        collection.addAll(homeTodoModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTodoViewHolder {
        val binding = FragmentDashboardTodoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeTodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: HomeTodoViewHolder, position: Int) {
        holder.bind(collection[position], clickLister)
    }
}