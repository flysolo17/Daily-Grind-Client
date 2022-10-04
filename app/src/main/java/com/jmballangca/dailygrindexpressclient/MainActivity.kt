package com.jmballangca.dailygrindexpressclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.jmballangca.dailygrindexpressclient.databinding.ActivityMainBinding
import com.jmballangca.dailygrindexpressclient.utils.UiState
import com.jmballangca.dailygrindexpressclient.viewmodels.OtpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val otpViewModel : OtpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        otpViewModel.number.observe(this) { state ->
            when(state) {
                is UiState.Loading -> {
                    Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show()
                }
                is UiState.Failure -> {
                    Toast.makeText(this,state.message,Toast.LENGTH_LONG).show()
                    binding.result.text = state.message
                }
                is UiState.Success -> {
                   binding.result.text = state.data.otp
                }
            }

        }
        binding.buttonCheckPhoneNumber.setOnClickListener {
            otpViewModel.checkPhoneNumber("09776989425")
        }
    }
}