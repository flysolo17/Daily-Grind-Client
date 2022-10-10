package com.jmballangca.dailygrindexpressclient.views.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.databinding.FragmentAccountInfoBinding
import com.jmballangca.dailygrindexpressclient.utils.ProgressDialog
import com.jmballangca.dailygrindexpressclient.utils.ROLE
import com.jmballangca.dailygrindexpressclient.utils.UiState
import com.jmballangca.dailygrindexpressclient.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountInfoFragment : Fragment() {
    private lateinit var binding : FragmentAccountInfoBinding
    private lateinit var progressDialog: ProgressDialog
    private val args : AccountInfoFragmentArgs by navArgs()
    private val authViewModel : AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAccountInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(view.context)
        isTermsAccepted(binding.checkbox1)
        isTermsAccepted(binding.checkbox2)

        authViewModel.registration.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    progressDialog.showLoadingDialog("Creating new account....")
                }
                is UiState.Failure -> {
                    progressDialog.stopLoading()
                    Toast.makeText(view.context,state.message,5000).show()
                }
                is UiState.Success -> {
                    progressDialog.stopLoading()
                    Toast.makeText(view.context,"New Account Created!",Toast.LENGTH_LONG).show()
                    authViewModel.login(args.phone,binding.inputPassword.text.toString())
                }
            }
        }
        authViewModel.login.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    progressDialog.showLoadingDialog("Logging in....")
                }
                is UiState.Failure -> {
                    progressDialog.stopLoading()
                    Toast.makeText(view.context,state.message,5000).show()
                }
                is UiState.Success -> {
                    progressDialog.stopLoading()
                    findNavController().navigate(R.id.action_accountInfoFragment_to_mainActivity)
                }
            }
        }

        binding.buttonSingUp.setOnClickListener {
            val password = binding.inputPassword.text.toString()
            val confirm = binding.inputConfirmPassword.text.toString()

            if (!binding.checkbox1.isChecked || !binding.checkbox2.isChecked) {
                Toast.makeText(view.context,"Make sure you agree to all our terms and conditions",Toast.LENGTH_LONG).show()
            }

            if (!validateName(binding.inputFirstname) ||
                !validateName(binding.inputLastname) ||
                !validateEmail(binding.inputEmail) ||
                !validatePassword(binding.inputPassword) ||
                !validatePassword(binding.inputConfirmPassword)) {
                return@setOnClickListener
            }

            if (password != confirm) {
                binding.inputConfirmPassword.error = "Password don't match"
                return@setOnClickListener
            }

            authViewModel.register(generateUser())
        }
    }


    private fun isTermsAccepted(checkBox1: CheckBox) {
        checkBox1.setOnCheckedChangeListener { _ , isChecked ->
            binding.buttonSingUp.isEnabled = isChecked
        }
    }

    private fun generateUser() : CustomerRegistrationRequest {
        val otp = args.otp
        val firstname = binding.inputFirstname.text.toString()
        val lastname = binding.inputLastname.text.toString()
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        val confirm = binding.inputConfirmPassword.text.toString()
        val phone = args.phone
        return CustomerRegistrationRequest(otp,firstname,lastname,email,password,confirm, ROLE,phone)
    }
}