package com.example.todolistapp.vvm.dashboard.ui.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.example.todolistapp.R
import com.example.todolistapp.vvm.dashboard.ui.adapter.HomeCategoryAdapter
import com.example.todolistapp.vvm.dashboard.ui.model.HomeCategoryModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * A simple [Fragment] subclass.
 * Use the [HomeAddBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeAddBottomSheetFragment(private val context: Context, private val adapterCategory: HomeCategoryAdapter) : BottomSheetDialogFragment() {
    private lateinit var pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the activity result launcher
        pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                Toast.makeText(context, "Selected URI: $uri", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "No media selected", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard_add_bottom_sheet, container, false)

        view.apply {
            findViewById<CardView>(R.id.cvChoosePic).setOnClickListener {
                // Launch the photo picker and let the user choose only images.
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            findViewById<TextView>(R.id.tvAddPic).setOnClickListener {
                adapterCategory.addToList(
                    HomeCategoryModel(
                        findViewById<EditText>(R.id.etNameBottomSheet).text.toString(),
                        "・0 task",
                        Uri.parse("android.resource://com.example.todolistapp/drawable/dashboard_home_add_category_photo_picker")
                    )
                )
                adapterCategory.notifyDataSetChanged()
                Toast.makeText(context, "Categories successfully updated", Toast.LENGTH_LONG).show()
                dismiss()
            }
        }

        return view
    }
}
