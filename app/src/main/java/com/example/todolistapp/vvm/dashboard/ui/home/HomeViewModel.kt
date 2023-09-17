package com.example.todolistapp.vvm.dashboard.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.todolistapp.data.dbAuth.FirebaseAuth
import com.example.todolistapp.data.dbFirestore.FirebaseFirestore
import com.example.todolistapp.vvm.dashboard.ui.adapter.HomeTodoAdapter
import com.example.todolistapp.vvm.dashboard.ui.model.HomeTodoModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Comparator
import java.util.Locale

class HomeViewModel : ViewModel() {
    private val TAG: String = "MyTag"

    fun getShortestName(names: List<String>): String {
        return "hello, ${names.stream().min(Comparator.comparing(String::length)).get()}!"
    }

    fun getRecyclerViewData(adapter: HomeTodoAdapter): List<HomeTodoModel> {
        val listHomeTodoModel = ArrayList<HomeTodoModel>()

        FirebaseFirestore().firestore.collection(FirebaseAuth.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listHomeTodoModel.add(
                        HomeTodoModel(
                            document.get("title").toString(),
                            document.get("subtitle").toString(),
                            document.get("time").toString(),
                            document.get("date").toString(),
                            document.get("category").toString(),
                            document.get("notes").toString()
                        )
                    )
                }
                // Set the list in the adapter and notify the adapter of the data change
                adapter.setList(listHomeTodoModel)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return listHomeTodoModel
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val dayInNumberForm = dayOfMonth.toString()
        val monthInLetterForm = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)
        val dayOfWeekInLetter = when(dayOfWeek) {
            Calendar.MONDAY -> "monday"
            Calendar.TUESDAY -> "tuesday"
            Calendar.WEDNESDAY -> "wednesday"
            Calendar.THURSDAY -> "thursday"
            Calendar.FRIDAY -> "friday"
            Calendar.SATURDAY -> "saturday"
            Calendar.SUNDAY -> "sunday"
            else -> throw UnknownError("Unknown day of week")
        }

        return "$dayOfWeekInLetter, ${dayInNumberForm}th $monthInLetterForm"
    }
}