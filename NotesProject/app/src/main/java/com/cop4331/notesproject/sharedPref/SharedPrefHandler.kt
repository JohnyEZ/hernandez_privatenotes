package com.cop4331.notesproject.sharedPref

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPrefHandler(context: Context) {

    private val PREF_NAME = "Pin"
    val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getPin(): Int {
        return preference.getInt("Pincode",0)
    }

    fun setPin(pin: Int){
        preference.edit().putInt("Pincode", pin).commit()
    }

    fun getAccess(): Boolean{
        return preference.getBoolean("Access",false)
    }

    fun setAccess(access: Boolean){
        preference.edit().putBoolean("Access",access).commit()
    }

    fun getLayout(): String? {
        return preference.getString("Layout","Linear")
    }

    fun setLayout(layout: String){
        preference.edit().putString("Layout", layout).commit()
    }
}
