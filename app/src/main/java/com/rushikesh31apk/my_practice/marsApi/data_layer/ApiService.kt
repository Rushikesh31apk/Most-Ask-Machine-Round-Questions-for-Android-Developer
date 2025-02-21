package com.rushikesh31apk.my_practice.marsApi.data_layer

import com.rushikesh31apk.my_practice.marsApi.data_layer.models.MarsPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsApiService {
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10 // Fetch 10 items per request
    ): List<MarsPhoto>
}