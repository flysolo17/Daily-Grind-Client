package com.jmballangca.dailygrindexpressclient.repository.order

import com.jmballangca.dailygrindexpressclient.data.request.CreateOrderRequest
import com.jmballangca.dailygrindexpressclient.data.response.CreateOrderResponse
import com.jmballangca.dailygrindexpressclient.repository.auth.AuthRepository
import com.jmballangca.dailygrindexpressclient.service.AuthService
import com.jmballangca.dailygrindexpressclient.service.OrderService
import com.jmballangca.dailygrindexpressclient.utils.CREATED
import com.jmballangca.dailygrindexpressclient.utils.UiState
import retrofit2.HttpException
import java.io.IOException

class OrderRepositoryImpl(private val orderService: OrderService) : OrderRepository {

    override suspend fun createOrder(
        tokenType : String,
        token : String,
        createOrderRequest: CreateOrderRequest,
        result: (UiState<CreateOrderResponse>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        try {
            //user == user token
            val response = orderService.createOrder("$tokenType $token",createOrderRequest)
            if (response.code() != CREATED) {
                result.invoke(UiState.Failure("${response.code()} : ${response.body()}"))
                return
            }
            result.invoke(UiState.Success(response.body()!!))
        } catch (e : HttpException) {
            result.invoke(UiState.Failure(e.message()))
        } catch (e : IOException) {
            result.invoke(UiState.Failure(e.message!!))
        }
    }

}