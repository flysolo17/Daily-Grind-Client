package com.jmballangca.dailygrindexpressclient.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.*
import com.jmballangca.dailygrindexpressclient.repository.AuthRepository
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

/*    private var user = MutableLiveData<User>()
    fun setUser(user: User) {
        this.user.value = user
    }
    fun getUser() : LiveData<User> {
        return user
    }*/

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

    fun saveUser(token : String ,tokenType : String) {
        viewModelScope.launch {
            authRepository.setUser(token, tokenType)
        }
    }

    fun getCurrentUser(): String? = runBlocking {
        authRepository.getUser()
    }
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}