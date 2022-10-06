package com.jmballangca.dailygrindexpressclient.views.registration

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmballangca.dailygrindexpressclient.databinding.FragmentVerificationBinding
import com.jmballangca.dailygrindexpressclient.utils.UiState
import com.jmballangca.dailygrindexpressclient.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {
    private lateinit var binding : FragmentVerificationBinding

    private val args : VerificationFragmentArgs by navArgs()
    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVerificationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textPhone.text = args.phone
        authViewModel.checkPhoneNumber(args.phone)

        authViewModel.number.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    verificationCodeCountDown()
                }
                is UiState.Failure -> {
                    Toast.makeText(view.context,state.message, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    binding.inputOtp.setText(state.data.otp)
                }
            }
        }

        authViewModel.verify.observe(viewLifecycleOwner) {state ->
            when (state) {
                is UiState.Loading -> {
                    binding.buttonVerify.isEnabled = false
                }
                is UiState.Failure -> {
                    binding.buttonVerify.isEnabled = true
                    Toast.makeText(view.context,state.message,Toast.LENGTH_LONG).show()
                }

                is UiState.Success -> {
                    val otp : String = binding.inputOtp.text.toString()
                    val action = VerificationFragmentDirections.actionVerificationFragmentToAccountInfoFragment(args.phone,otp)
                    findNavController().navigate(action)
                    binding.buttonVerify.isEnabled = true
                }
            }
        }

        binding.inputOtp.addTextChangedListener { text: Editable? ->
            if (text.toString().length == 6) {
                binding.buttonVerify.isEnabled = true
            }
        }

        binding.buttonVerify.setOnClickListener {
            authViewModel.checkOtp(binding.inputOtp.text.toString())
        }

        binding.buttonResend.setOnClickListener {
            authViewModel.checkPhoneNumber(args.phone)
        }
    }

    //Verification Countdown. (60 seconds)
    private fun verificationCodeCountDown() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.buttonResend.text = "" + millisUntilFinished / 1000
                binding.buttonResend.isEnabled = false
            }

            override fun onFinish() {
                binding.buttonResend.text = "Resend"
                binding.buttonResend.isEnabled = true
            }
        }.start()
    }
}