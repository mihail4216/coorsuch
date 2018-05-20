package com.example.mihail.taxitest.activity

import android.app.Activity
import android.os.Bundle
import com.example.mihail.taxitest.R


class SearchActivity: Activity(){

    companion object {
        val EXTRA_whence = "whence"
        val EXTRA_where = "where"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


    }
}