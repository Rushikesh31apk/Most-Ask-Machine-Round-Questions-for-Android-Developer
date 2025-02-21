package com.rushikesh31apk.my_practice.room_practice.data.repository

import com.rushikesh31apk.my_practice.room_practice.data.dao.TodoDao
import com.rushikesh31apk.my_practice.room_practice.data.model.Todo
import kotlinx.coroutines.flow.Flow

class Repo(private  val dao: TodoDao) {

    val todos: Flow<List<Todo>> = dao.getTodos()

    suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }
    suspend fun deleteTodoById(todo: Todo) {
        dao.deleteTodoById(todo)
    }
    suspend fun upsertTodo(todo: Todo) {
        dao.upsertTodo(todo)
    }
}