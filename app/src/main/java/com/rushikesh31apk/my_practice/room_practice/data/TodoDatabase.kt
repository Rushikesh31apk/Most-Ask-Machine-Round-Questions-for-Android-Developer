package com.rushikesh31apk.my_practice.room_practice.data

// Import necessary Android and Room library classes
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rushikesh31apk.my_practice.room_practice.data.model.Todo
import com.rushikesh31apk.my_practice.room_practice.data.dao.TodoDao

// Define the Room Database with the entity 'Todo' and version number
@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    // Abstract method to get the DAO instance for database operations
    abstract val dao: TodoDao

    // Companion object to implement the Singleton pattern for the database instance
    companion object {

        // Volatile ensures visibility of changes to INSTANCE across threads
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        // Database name constant
        const val DATABASE_NAME = "todo_db"

        // Function to get the database instance
        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) { // Synchronizing to prevent multiple instances
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Use application context to prevent memory leaks
                    TodoDatabase::class.java, // Specify the database class
                    DATABASE_NAME // Set the database name
                ).build()

                INSTANCE = instance // Assign the created instance to the INSTANCE variable
                instance // Return the instance
            }
        }
    }
}
