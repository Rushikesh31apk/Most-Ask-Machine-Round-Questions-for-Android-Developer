package com.rushikesh31apk.my_practice.marsApi.data_layer

import com.rushikesh31apk.my_practice.marsApi.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance {
    private val _retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarsApiService::class.java)

    val api: MarsApiService = _retrofit
}
