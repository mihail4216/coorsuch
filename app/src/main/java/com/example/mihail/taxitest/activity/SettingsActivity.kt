package com.example.mihail.taxitest.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.mihail.taxitest.R
import com.example.mihail.taxitest.repository.MainRepository


class SettingsActivity : Activity() {

    private lateinit var backBtn: View
    private lateinit var homeTxt: EditText
    private lateinit var phoneTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        backBtn = findViewById(R.id.back)
        homeTxt = findViewById(R.id.home)
        phoneTxt = findViewById(R.id.phone)
        backBtn.setOnClickListener {
            finish()
        }

        homeTxt.text.append(MainRepository.instance.homeAddress)
        phoneTxt.text.append(MainRepository.instance.phone)

    }

    override fun onPause() {
        super.onPause()
        MainRepository.instance.homeAddress = homeTxt.text.toString()
        MainRepository.instance.phone = phoneTxt.text.toString()
    }
}