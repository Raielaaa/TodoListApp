package com.example.todolistapp.vvm.account.ui.Login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolistapp.data.DBDataViewModel
import com.example.todolistapp.data.dbAuth.FirebaseAuth
import com.example.todolistapp.vvm.LoadingDialog
import com.example.todolistapp.vvm.accounts.ui.main.MainActivity
import com.example.todolistapp.vvm.dashboard.DashboardActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    var loginWithSameEmailCounter: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = 1
    }
    private val emailUsedForLogin: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val TAG: String = "MyTag"
    private var currentEmail: String = ""
    private var currentPass: String = ""
    private lateinit var loadingDialog: LoadingDialog

    // Validate user input for firebase auth
    fun validateEmailPassword(email: String, password: String, mainActivity: MainActivity, context: Context, etEmail: EditText, etPassword: EditText, activity: Activity) {
        currentEmail = email
        currentPass = password
        if (email.isNotEmpty() && password.isNotEmpty()) {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        loadingDialog = LoadingDialog(activity)
                        loadingDialog.startLoadingDialog()
                    }
                    FirebaseAuth.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            loadingDialog.dismissDialog()
                            mainActivity.startActivity(Intent(context, DashboardActivity::class.java))
                        }
                    }.addOnFailureListener { exception ->
                        if (emailUsedForLogin.value == email) loginWithSameEmailCounter.value = loginWithSameEmailCounter.value!! + 1
                        else {
                            emailUsedForLogin.value = email
                            loginWithSameEmailCounter.value = 1
                        }

                        Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                        etPassword.setText("")
                    }
                }
            } catch (ignored: Exception) {
                etEmail.setText("")
                etPassword.setText("")
                Log.i(TAG, ignored.localizedMessage!!)
            }
        } else Toast.makeText(context, "Please fill all of the fields", Toast.LENGTH_LONG).show()
    }

    // Delete user from firebase auth
    fun deleteUser() {
        val credential: AuthCredential = EmailAuthProvider.getCredential(currentEmail, currentPass)

        val user = FirebaseAuth.auth.currentUser!!
        user.reauthenticate(credential)
            .addOnCompleteListener {
                user.delete()
                    .addOnCompleteListener {
                        Log.i(TAG, "User account deleted successfully")
                    }
            }
    }
}