package com.authencation.firebaseemailpasswordauthencation

import android.text.TextUtils
import android.util.Patterns


class ValidateInputFields private constructor() {

    companion object{
        @JvmStatic
        @Volatile
        private var instance:ValidateInputFields?=null
        fun getInstance() = instance?:
        synchronized(this){
            instance?: ValidateInputFields().also { instance=it }
        }
    }
    fun isValidEmail(email:String):Boolean{
        if(TextUtils.isEmpty(email))  return false
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())  return false
        return true
    }
    fun isValidPassword(password:String):Boolean{
        if(TextUtils.isEmpty(password))  return false
        if(password.length<8) return false
        return true
    }
    fun isValidConfirmPassword(password:String,confirmPassword: String):Boolean{
        return confirmPassword == password
    }
    fun isValidUser(email: String,password: String):Boolean{
        return isValidEmail(email) && isValidPassword(password)
    }
    fun isValidUserRegister(email: String,password: String,confirmPassword:String):Boolean{
        return isValidEmail(email) && isValidPassword(password) && confirmPassword == password
    }
}