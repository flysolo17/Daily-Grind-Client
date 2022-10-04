package com.jmballangca.dailygrindexpressclient.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmballangca.dailygrindexpressclient.data.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.repository.AuthRepository
import com.jmballangca.dailygrindexpressclient.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private var phoneNumber = MutableLiveData<UiState<CheckPhoneNumberResponse>>()
    val number: LiveData<UiState<CheckPhoneNumberResponse>>
        get() = phoneNumber

     fun checkPhoneNumber(number : String) {
         viewModelScope.launch {
             UiState.Loading
             authRepository.checkPhoneNumber(number) {
                 phoneNumber.value = it
             }
         }
    }
}