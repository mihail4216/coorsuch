package com.example.mihail.taxitest.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class MainRepository private constructor() {
    private val APP_PREFERENCES = "mysettings"
    private val HOME_ED = "home"
    private val PHONE_ED = "phone"

    private object Holder {
        val instance = MainRepository()
    }

    companion object {
        val instance: MainRepository by lazy { Holder.instance }
    }

    //    private lateinit var context: Context
    fun init(context: Context) {
//        this.context = context
        data = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    lateinit var data: SharedPreferences


    var phone: String
        get() {
            return data.getString(PHONE_ED, "")
        }
        set(s) {
            val ed = data.edit()
            ed.putString(PHONE_ED, s)
            ed.apply()
        }


    var homeAddress: String
        get() {
            return data.getString(HOME_ED, "")
        }
        @SuppressLint("CommitPrefEdits")
        set(s) {
            val ed = data.edit()
            ed.putString(HOME_ED, s)
            ed.apply()
        }
}
