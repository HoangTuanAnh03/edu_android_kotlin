package com.anhht.edu.config

import android.content.Context
import android.content.SharedPreferences
import com.anhht.edu.R

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    
    companion object {
        const val USER_ACCESS_TOKEN = "user_access_token"
        const val USER_REFRESH_TOKEN = "user_refresh_token"
        const val USER_EMAIL = "user_email"
        const val USER_PASSWORD = "user_password"
        const val APP_REMEMBER_PASSWORD = "app_remember_password"
        const val APP_STATE_LOGIN = "app_state_login"
    }

    fun saveAuthAccessToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_ACCESS_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthAccessToken(): String? {
        return prefs.getString(USER_ACCESS_TOKEN, null)
    }

    fun saveAuthRefreshToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_REFRESH_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthRefreshToken(): String? {
        return prefs.getString(USER_REFRESH_TOKEN, null)
    }

    fun saveEmail(email: String) {
        val editor = prefs.edit()
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }

    fun fetchEmail(): String? {
        return prefs.getString(USER_EMAIL, null)
    }

    fun savePassWord(pass: String) {
        val editor = prefs.edit()
        editor.putString(USER_PASSWORD, pass)
        editor.apply()
    }

    fun fetchPassWord(): String? {
        return prefs.getString(USER_PASSWORD, null)
    }


    fun saveStateRememberPassword(state: String) {
        val editor = prefs.edit()
        editor.putString(APP_REMEMBER_PASSWORD, state)
        editor.apply()
    }

    fun fetchStateRememberPassword(): String? {
        return prefs.getString(APP_REMEMBER_PASSWORD, "false")
    }

    fun saveStateLogin(state: String) {
        val editor = prefs.edit()
        editor.putString(APP_STATE_LOGIN, state)
        editor.apply()
    }

    fun fetchStateLogin(): String? {
        return prefs.getString(APP_STATE_LOGIN, "false")
    }
}