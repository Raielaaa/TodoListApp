package com.example.todolistapp.vvm.accounts.ui.login

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.databinding.FragmentLoginBinding
import com.example.todolistapp.vvm.account.ui.Login.LoginViewModel
import com.example.todolistapp.vvm.accounts.ui.main.MainActivity
import com.example.todolistapp.vvm.accounts.ui.register.RegisterFragment

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var _binding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        val view = _binding.root

        initViewClickFunctions()
        initObservers()

        return view
    }

    private fun initObservers() {
        _binding.apply {
            loginViewModel.loginWithSameEmailCounter.observe(requireActivity()) {
                if (it > 2) {
                    loginViewModel.deleteUser()
                    etEmail.setText("")
                    tvTries.visibility = View.INVISIBLE
                } else {
                    tvTries.visibility = View.VISIBLE
                    val textWarning = if (it != 1) "${3 - it} tries remaining!*" else ""
                    tvTries.text = textWarning
                }
            }
        }
    }

    private fun initViewClickFunctions() {
        _binding.apply {
            tvAccount.setOnClickListener {
                mainActivity.showFragment(RegisterFragment())
            }
            btnLogin.setOnClickListener {
                loginViewModel.validateEmailPassword(etEmail.text.toString(), etPassword.text.toString(), mainActivity, requireContext(), etEmail, etPassword, requireActivity())
            }
            cbSeePassword.setOnClickListener {
                val currentType = etPassword.inputType

                if (currentType == InputType.TYPE_CLASS_TEXT) etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                else etPassword.inputType = InputType.TYPE_CLASS_TEXT
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}