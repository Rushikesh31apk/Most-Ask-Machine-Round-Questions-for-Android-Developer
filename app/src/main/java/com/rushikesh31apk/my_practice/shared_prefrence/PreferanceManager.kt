package com.rushikesh31apk.my_practice.shared_prefrence

import android.content.Context

// DataStore wrapper for SharedPreferences
object PreferencesManager {

    // Constants for SharedPreferences keys
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USERNAME = "username"
    private const val KEY_PASSWORD = "password"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    fun saveUserData(context: Context, username: String, password: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getUserData(context: Context): Triple<String?, String?, Boolean> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return Triple(
            sharedPreferences.getString(KEY_USERNAME, null),
            sharedPreferences.getString(KEY_PASSWORD, null),
            sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        )
    }

    fun clearUserData(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}