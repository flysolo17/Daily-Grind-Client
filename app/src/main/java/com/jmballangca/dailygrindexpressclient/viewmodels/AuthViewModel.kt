package com.jmballangca.dailygrindexpressclient.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.*
import com.jmballangca.dailygrindexpressclient.models.User
import com.jmballangca.dailygrindexpressclient.repository.auth.AuthRepository
import com.jmballangca.dailygrindexpressclient.utils.TOKEN
import com.jmballangca.dailygrindexpressclient.utils.TOKEN_TYPE
import com.jmballangca.dailygrindexpressclient.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private var phoneNumber = MutableLiveData<UiState<CheckPhoneNumberResponse>>() //get otp
    val number: LiveData<UiState<CheckPhoneNumberResponse>>
        get() = phoneNumber


    private var verifyOtp = MutableLiveData<UiState<CheckOtpResponse>>() //otp verification
    val verify: LiveData<UiState<CheckOtpResponse>>
        get() = verifyOtp

    private var customerRegistration = MutableLiveData<UiState<CustomerRegistrationResponse>>() //registration
    val registration: LiveData<UiState<CustomerRegistrationResponse>>
        get() = customerRegistration

    private var loginResponse = MutableLiveData<UiState<LoginResponse>>() //registration
    val login: LiveData<UiState<LoginResponse>>
        get() = loginResponse

    private var getProfileResponse = MutableLiveData<UiState<User>>() //Profile
    val profile: LiveData<UiState<User>>
        get() = getProfileResponse

    fun checkPhoneNumber(number : String) {
         viewModelScope.launch {
             authRepository.checkPhoneNumber(number) {
                 phoneNumber.value = it
             }
         }
    }

    fun checkOtp(otp : String) {
        viewModelScope.launch{
            authRepository.checkOtp(otp) {
                verifyOtp.value = it
            }
        }
    }
    fun register(customerRegistrationRequest: CustomerRegistrationRequest) {
        viewModelScope.launch {
            authRepository.customerRegistration(customerRegistrationRequest) {
                customerRegistration.value = it
            }
        }
    }

    fun login(phone : String ,password : String) {
        viewModelScope.launch {
            authRepository.login(phone,password) {
                loginResponse.value = it
            }
        }
    }

    fun saveUser(loginResponse: LoginResponse) {
        viewModelScope.launch {
            authRepository.setUser(loginResponse)
        }
    }

    fun getCurrentUser(key : String): String? = runBlocking {
        authRepository.getUser(key)
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
    fun getProfile() {
        val token = getCurrentUser(TOKEN)
        val token_type = getCurrentUser(TOKEN_TYPE)
        viewModelScope.launch {
            authRepository.userProfile("$token_type $token") {
                getProfileResponse.value = it
            }
        }
    }
}