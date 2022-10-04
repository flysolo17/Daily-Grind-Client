package com.jmballangca.dailygrindexpressclient.views.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.jmballangca.dailygrindexpressclient.R
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
                    Toast.makeText(view.context,"Loading", Toast.LENGTH_LONG).show()
                }
                is UiState.Failure -> {
                    Toast.makeText(view.context,state.message, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    binding.inputOtp.setText(state.data.otp)
                }
            }
        }
    }

}