package com.fabgod.bikestoreinventory.utils

import android.app.Activity
import android.content.Context

private const val PREFERENCES_KEY = "MyPrefs"
private const val SESSION_KEY = "isLoggedIn"

class SharedPreferencesInstance(activity: Activity) {

    private val sharedPreferences =
        activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveSession() {
        editor.putBoolean(SESSION_KEY, true)
        editor.apply()
    }

    fun getSession(): Boolean =
        sharedPreferences.getBoolean(SESSION_KEY, false)

    fun deleteSession() {
        editor.clear()
        editor.apply()
    }
}
