package com.jmballangca.dailygrindexpressclient.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmballangca.dailygrindexpressclient.data.request.CreateOrderRequest
import com.jmballangca.dailygrindexpressclient.data.response.CreateOrderResponse
import com.jmballangca.dailygrindexpressclient.repository.auth.AuthRepository
import com.jmballangca.dailygrindexpressclient.repository.order.OrderRepository
import com.jmballangca.dailygrindexpressclient.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val authRepository: AuthRepository,private val orderRepository: OrderRepository) : ViewModel() {
    private val createOrder = MutableLiveData<UiState<CreateOrderResponse>>()
    val order : LiveData<UiState<CreateOrderResponse>>
        get() = createOrder

    fun createOrder(createOrderRequest: CreateOrderRequest) {
        viewModelScope.launch {
            if (authRepository.getUser() != null) {
                orderRepository.createOrder(authRepository.getUser()!!,createOrderRequest) {
                    createOrder.value = it
                }
            }
        }
    }
}