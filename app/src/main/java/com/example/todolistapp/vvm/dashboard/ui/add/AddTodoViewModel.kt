package com.example.todolistapp.vvm.dashboard.ui.add

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.todolistapp.R
import com.example.todolistapp.data.dbAuth.FirebaseAuth
import com.example.todolistapp.data.dbFirestore.FirebaseFirestore
import com.example.todolistapp.vvm.LoadingDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class AddTodoViewModel : ViewModel() {
    private val TAG: String = "MyTag"
    private val calendar = Calendar.getInstance()
    private var calendarYear: Int = calendar.get(Calendar.YEAR)
    private var calendarMonth: Int = calendar.get(Calendar.MONTH)
    private var calendarDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
    private var firestoreDB: FirebaseFirestore = FirebaseFirestore()
    private lateinit var loadingDialog: LoadingDialog

    fun configureRealTimeCounter(tvCurrentTime: TextView, addTodoFragment: AddTodoFragment) {
        val lifecycleScope = addTodoFragment.requireActivity().lifecycleScope

        lifecycleScope.launch {
            while (true) {
                val philippinesTimeZone = TimeZone.getTimeZone("Asia/Manila")
                val timeFormat = SimpleDateFormat("HH:mm:ss")
                timeFormat.timeZone = philippinesTimeZone
                val currentTime = timeFormat.format(Date()).split(":")

                val hour = currentTime[0].toInt()
                val amPm = if (hour < 12) "AM" else "PM"
                val formattedHour = String.format("%02d", if (hour > 12) hour - 12 else hour)
                val formattedMinute = String.format("%02d", currentTime[1].toInt())
                val formattedSecond = String.format("%02d", currentTime[2].toInt())

                val currentFormattedTime = "$formattedHour:$formattedMinute:$formattedSecond $amPm"
                tvCurrentTime.text = currentFormattedTime
                delay(1000)
            }
        }
    }

    fun setDate(context: Context, tvSetDate: TextView) {
        val datePickerDialog: DatePickerDialog = DatePickerDialog(
            context, R.style.CustomDatePickerDialog, { _, year, monthOfYear, dayOfMonth ->
                val returnDate = "${monthOfYear + 1} $dayOfMonth $year"
                val date = StringHelper.parseDate(
                    "MM dd yyyy",
                    "MM/dd/yyyy",
                    returnDate
                )
                tvSetDate.text = StringHelper.parseDate("MM/dd/yyyy","MMM dd yyyy",date)
            },calendarYear-30, calendarMonth, calendarDay
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    fun setTime(addTodoFragment: AddTodoFragment, setTime: TextView) {
        val clockFormat = TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set ToDo time")
            .build()
        picker.show(addTodoFragment.childFragmentManager, TAG)

        picker.addOnPositiveButtonClickListener {
            val amPm = if (picker.hour < 12) "AM" else "PM"
            val selectedTime: String = "${String.format("%02d", if (picker.hour > 12) picker.hour - 12 else picker.hour)}:${String.format("%02d", picker.minute)} $amPm"
            setTime.text = selectedTime
        }
    }

    fun addToDB(etTitle: EditText, etSubtitle: EditText, etNotes: EditText, etSetTime: TextView, etSetdate: TextView, etCategory: EditText, context: Context, activity: Activity) {
        if (etTitle.text.isNotEmpty() && etSubtitle.text.isNotEmpty() && etSetTime.text.isNotEmpty() && etSetdate.text.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val todoEntry = hashMapOf(
                    "category" to etCategory.text.toString(),
                    "date" to etSetdate.text.toString(),
                    "time" to etSetTime.text.toString(),
                    "notes" to etNotes.text.toString(),
                    "subtitle" to etSubtitle.text.toString(),
                    "title" to etTitle.text.toString()
                )
                withContext(Dispatchers.Main) {
                    loadingDialog = LoadingDialog(activity)
                    loadingDialog.startLoadingDialog()
                }
                firestoreDB.firestore.collection(FirebaseAuth.auth.currentUser!!.uid)
                    .add(todoEntry)
                    .addOnSuccessListener {
                        loadingDialog.dismissDialog()

                        CoroutineScope(Dispatchers.Main).launch {
                            etTitle.setText("")
                            etSubtitle.setText("")
                            etNotes.setText("")
                            etSetTime.text = ""
                            etSetdate.text = ""
                            etCategory.setText("")

                            val navController = activity.findNavController(R.id.nav_host_fragment_activity_dashboard)
                            navController.navigate(R.id.navigation_home)

                            Toast.makeText(context, "ToDo successfully added", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document $e")
                    }
            }
        } else Toast.makeText(context, "Please fill all the required values {e.g. Heading, Subtitle, Time, and Date}", Toast.LENGTH_LONG).show()
    }
}