package com.rushikesh31apk.my_practice.api_practice.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 3. Retrofit Client
object RetrofitClient {
    private const val BASE_URL = "https://dd2c-103-133-159-145.ngrok-free.app/api/"

    private val _retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TodoApiService::class.java)

    val api: TodoApiService = _retrofit
}
