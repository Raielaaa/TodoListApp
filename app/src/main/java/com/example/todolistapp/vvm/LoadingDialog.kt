package com.example.todolistapp.vvm

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import com.example.todolistapp.R

class LoadingDialog(private val activity: Activity) {
    private lateinit var alertDialog: AlertDialog

    @SuppressLint("InflateParams")
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        val view: View = inflater.inflate(R.layout.progress_bar, null)
        builder.setView(view)
        builder.setCancelable(false)

        alertDialog = builder.create() // Initialize alertDialog here
        alertDialog.show()
    }

    fun dismissDialog() {
        alertDialog.dismiss()
    }
}