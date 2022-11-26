package com.jmballangca.dailygrindexpressclient.service

import com.jmballangca.dailygrindexpressclient.data.request.CreateOrderRequest
import com.jmballangca.dailygrindexpressclient.data.response.CreateOrderResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderService {


    @POST("order")

    suspend fun createOrder(@Header("Authorization") token : String,
                    @Body createOrderRequest: CreateOrderRequest) : Response<CreateOrderResponse?>


}