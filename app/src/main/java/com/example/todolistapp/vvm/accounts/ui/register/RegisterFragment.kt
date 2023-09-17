package com.example.todolistapp.vvm.accounts.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.R
import com.example.todolistapp.data.dbAuth.FirebaseAuth
import com.example.todolistapp.databinding.FragmentRegisterBinding
import com.example.todolistapp.vvm.accounts.ui.login.LoginFragment
import com.example.todolistapp.vvm.accounts.ui.main.MainActivity
import com.example.todolistapp.vvm.dashboard.DashboardActivity

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var _binding: FragmentRegisterBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        registerViewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
        val view = _binding.root

        _binding.apply {
            btnRegister.setOnClickListener {
                registerViewModel.createUserToFirebase(etName, etEmail, etPassword, requireContext(), mainActivity, requireActivity())
            }
            tvAlreadyHaveAnAccount.setOnClickListener {
                mainActivity.showFragment(LoginFragment())
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}