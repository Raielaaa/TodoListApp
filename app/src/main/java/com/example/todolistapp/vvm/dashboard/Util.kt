package com.example.todolistapp.vvm.dashboard

import android.net.Uri
import com.example.todolistapp.vvm.dashboard.ui.model.HomeCategoryModel
import com.example.todolistapp.vvm.dashboard.ui.model.HomeTodoModel

object Util {
    val homeTodoList: List<HomeTodoModel> = listOf(
        HomeTodoModel("Meeting: Ux Case", "Discuss website function", "2m", "Work"),
        HomeTodoModel("Fitness - 2 hours", "Daily workout routine", "1h", "Health"),
        HomeTodoModel("Mobile app function", "Adding button functionality", "42m", "Hobby"),
        HomeTodoModel("Windows OS install", "Dual booting my SSD", "3h", "Personal"),
        HomeTodoModel("Supermarket", "Buying essential food supplies", "28m", "Shopping"),
    )

    val homeCategoryList: List<HomeCategoryModel> = listOf(
        HomeCategoryModel("Work", "・8 task", Uri.parse("android.resource://com.example.todolistapp/drawable/dashboard_home_category_briefcase")),
        HomeCategoryModel("Kitchen", "・13 task", Uri.parse("android.resource://com.example.todolistapp/drawable/dashboard_home_category_trolley")),
        HomeCategoryModel("School", "・7 task", Uri.parse("android.resource://com.example.todolistapp/drawable/dashboard_home_category_textbook"))
    )
}