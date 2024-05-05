package com.anhht.edu.utils

import android.util.Patterns
import android.widget.EditText

object ValidateDataUtil {
    fun isEmail(email : String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

    fun isPassWord(password : String) :Boolean {
        return password.length >= 8
    }

    fun isEmailAndEmpty(editText: EditText) : Boolean{
        if (editText.text.toString().trim().isEmpty()) {
            editText.error = "Email not empty!"
            return false;
        }
        if (!isEmail(editText.text.toString().trim())
        ) {
            editText.error = "Invalid email format"
            return false;
        }
        return true
    }
}