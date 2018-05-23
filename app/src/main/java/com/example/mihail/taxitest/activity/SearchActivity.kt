package com.example.mihail.taxitest.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import com.example.mihail.taxitest.R


class SearchActivity : Activity() {

    companion object {
        val EXTRA_whence = "whence"
        val EXTRA_where = "where"
    }

    private lateinit var whereTxt: EditText
    private lateinit var whenceTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        whenceTxt = findViewById(R.id.whence)
        whereTxt = findViewById(R.id.where)

        whereTxt.text.append(intent.getStringExtra(EXTRA_where))
        whenceTxt.text.append(intent.getStringExtra(EXTRA_whence))

    }
}