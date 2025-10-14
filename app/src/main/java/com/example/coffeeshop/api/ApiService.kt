package com.example.coffeeshop.api

import com.example.coffeeshop.model.LoginRequest
import com.example.coffeeshop.model.LoginResponse
import com.example.coffeeshop.model.RegisterRequest
import com.example.coffeeshop.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}