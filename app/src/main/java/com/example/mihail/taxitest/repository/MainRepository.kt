package com.example.mihail.taxitest.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class MainRepository private constructor() {
    private val APP_PREFERENCES = "mysettings"
    private val HOME_ED = "home"
    private val PHONE_ED = "phone"
    private val IS_PAYCARD_ED = "isCard"

    private object Holder {
        val instance = MainRepository()
    }

    companion object {
        val instance: MainRepository by lazy { Holder.instance }
    }

    lateinit var data: SharedPreferences

    fun init(context: Context) {
        data = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }


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

    var isPayCard: Boolean
        get() {
            return data.getBoolean(IS_PAYCARD_ED, false)
        }
        @SuppressLint("CommitPrefEdits")
        set(s) {
            val ed = data.edit()
            ed.putBoolean(IS_PAYCARD_ED, s)
            ed.apply()
        }
}
