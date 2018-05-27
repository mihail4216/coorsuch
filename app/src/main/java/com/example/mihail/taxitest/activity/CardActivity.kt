package com.example.mihail.taxitest.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import com.example.mihail.taxitest.R


class CardActivity : Activity() {

    private lateinit var whereTxt: EditText
    private lateinit var whenceTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

//        whenceTxt = findViewById(R.id.whence)
//        whereTxt = findViewById(R.id.where)
//
//        whereTxt.text.append(intent.getStringExtra(EXTRA_where))
//        whenceTxt.text.append(intent.getStringExtra(EXTRA_whence))

    }
}