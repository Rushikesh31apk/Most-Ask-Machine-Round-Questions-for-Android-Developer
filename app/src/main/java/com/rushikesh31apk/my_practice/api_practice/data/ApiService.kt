package com.rushikesh31apk.my_practice.api_practice.data

import com.rushikesh31apk.my_practice.api_practice.data.models.NewTodoInput
import com.rushikesh31apk.my_practice.api_practice.data.models.TodoResponce
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// 2. API Service Interface
interface TodoApiService {
    @GET("todos/")
    suspend fun getAllTodos(): List<TodoResponce>

    @GET("todos/{id}/")
    suspend fun getTodo(
        @Path("id") id: Int
    ): TodoResponce

    @POST("todos/")
    suspend fun createTodo(
        @Body todo: NewTodoInput
    ): TodoResponce

    @PUT("todos/{id}/")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: TodoResponce
    ): TodoResponce

    @DELETE("todos/{id}/")
    suspend fun deleteTodo(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("todos/")
    suspend fun searchTodos(
        @Query("search") query: String
    ): List<TodoResponce>
}