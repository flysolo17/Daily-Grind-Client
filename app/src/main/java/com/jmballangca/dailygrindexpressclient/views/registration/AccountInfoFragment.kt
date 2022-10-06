package com.jmballangca.dailygrindexpressclient.views.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.databinding.FragmentAccountInfoBinding
import com.jmballangca.dailygrindexpressclient.utils.Constants
import com.jmballangca.dailygrindexpressclient.utils.UiState
import com.jmballangca.dailygrindexpressclient.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountInfoFragment : Fragment() {
    private lateinit var binding : FragmentAccountInfoBinding
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


        authViewModel.registration.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    MaterialAlertDialogBuilder(view.context)
                        .setTitle("Error!")
                        .setMessage(state.message)
                        .show()
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(view.context,"New Account Created!",Toast.LENGTH_LONG).show()
                }
            }

        }
        binding.buttonSingUp.setOnClickListener {
            val otp = args.otp
            val firstname = binding.inputFirstname.text.toString()
            val lastname = binding.inputLastname.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val confirm = binding.inputConfirmPassword.text.toString()
            val phone = args.phone

            authViewModel.register(CustomerRegistrationRequest(
                otp = otp,
                first_name =  firstname,
                last_name = lastname,
                email = email,
                password = password,
                password_confirmation = confirm,
                role = Constants.ROLE,
                phone_number = phone))
        }
    }



}