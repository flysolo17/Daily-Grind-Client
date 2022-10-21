package com.jmballangca.dailygrindexpressclient.repository.order

import com.jmballangca.dailygrindexpressclient.data.request.CreateOrderRequest
import com.jmballangca.dailygrindexpressclient.data.response.CreateOrderResponse
import com.jmballangca.dailygrindexpressclient.utils.UiState

interface OrderRepository {
    suspend fun createOrder(user : String, createOrderRequest: CreateOrderRequest, result : (UiState<CreateOrderResponse>) -> Unit)
}