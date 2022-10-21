package com.jmballangca.dailygrindexpressclient.views.registration

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmballangca.dailygrindexpressclient.MainActivity
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.databinding.FragmentAccountInfoBinding
import com.jmballangca.dailygrindexpressclient.databinding.FragmentLoginBinding
import com.jmballangca.dailygrindexpressclient.utils.ProgressDialog
import com.jmballangca.dailygrindexpressclient.utils.UiState
import com.jmballangca.dailygrindexpressclient.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireActivity())
        authViewModel.login.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UiState.Loading -> {
                    progressDialog.showLoadingDialog("Logging in...")
                }
                is UiState.Failure -> {
                    progressDialog.stopLoading()
                    Toast.makeText(view.context,state.message,5000).show()
                }
                is UiState.Success -> {
                    authViewModel.saveUser(state.data.data.access_token)
                    progressDialog.stopLoading()
                    Toast.makeText(view.context,"Successfully logged in!",5000).show()
                    startActivity(Intent(activity, MainActivity::class.java))
                }
            }
        }
        binding.buttonLogin.setOnClickListener {
            if (!validatePhoneNumber(binding.inputPhone) || !validatePassword(binding.inputPassword)) return@setOnClickListener
            authViewModel.login(binding.inputPhone.text.toString(),binding.inputPassword.text.toString())
        }
    }
}