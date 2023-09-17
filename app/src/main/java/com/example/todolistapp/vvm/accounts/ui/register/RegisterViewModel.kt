package com.example.todolistapp.vvm.accounts.ui.register

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.todolistapp.data.dbAuth.FirebaseAuth
import com.example.todolistapp.vvm.LoadingDialog
import com.example.todolistapp.vvm.accounts.ui.login.LoginFragment
import com.example.todolistapp.vvm.accounts.ui.main.MainActivity
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {
    private val TAG: String = "MyTag"
    private lateinit var loadingDialog: LoadingDialog
    // Create user for firebase
    fun createUserToFirebase(etName: EditText, etEmail: EditText, etPassword: EditText, context: Context, mainActivity: MainActivity, activity: Activity) {
        val user = FirebaseAuth.auth

        if (etName.text.isNotEmpty() &&
            etEmail.text.isNotEmpty() &&
            etPassword.text.isNotEmpty()) {
            val name: String = etName.text.toString()
            val email: String = etEmail.text.toString()
            val password: String = etPassword.text.toString()

            try {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        loadingDialog = LoadingDialog(activity)
                        loadingDialog.startLoadingDialog()
                    }
                    user.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Set the display name for user
                            val userProfile = UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build()

                            user.currentUser!!.updateProfile(userProfile).addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {
                                    loadingDialog.dismissDialog()
                                    mainActivity.showFragment(LoginFragment())
                                    Toast.makeText(context, "Register successful", Toast.LENGTH_LONG).show()
                                } else {
                                    // Handle the case where updating the display name failed
                                    Toast.makeText(context, "Failed to set display name", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(context, exception.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } catch (ignored: Exception) {
                etName.setText("")
                etEmail.setText("")
                etPassword.setText("")
                Log.i(TAG, ignored.localizedMessage!!)
            }
        } else Toast.makeText(context, "Please input all of the fields", Toast.LENGTH_LONG).show()
    }
}